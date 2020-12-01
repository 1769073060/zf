package com.jiyeyihe.cre.webapp.web.app;





import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.WXPay.*;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static com.jiyeyihe.cre.commons.WXPay.WXPayConstants.SUCCESS;
import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "app支付功能")
@RestController
@RequestMapping("/app/payment/")
public class PaymentController {

    @Resource
    private IHpOrderService iHpOrderService;
    @Resource
    private IObOrderService iObOrderService;
    @Resource
    private Environment environment;
    @Resource
    private IHpCustomerViewingRecordService iHpCustomerViewingRecordService;
    @Resource
    private IObCustomerViewingRecordService iObCustomerViewingRecordService;
    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private IObInfoDetailService iObInfoDetailService;


    /*调用支付接口*/
    @ApiOperation(httpMethod = "POST", value = "调用支付接口")
    @RequestMapping(value = "prePay", method = RequestMethod.POST)
    public Map<String, Object> prePay(@RequestBody Pay pay,HttpServletRequest request){
        log.info("调用支付接口");
        HpOrder hpOrder = new HpOrder();
        ObOrder obOrder = new ObOrder();
        QueryWrapper queryWrapper = new QueryWrapper();
        // 返回参数
        Map<String, Object> resMap = new HashMap<>();
        //获取当前请求ip地址
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.indexOf(",")!=-1){
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }

        try {

            if (pay.getType()==0){
                HpOrder idOrder= iHpOrderService.getByIdOrder(pay.getOrdersNo());
                if (idOrder!=null){
                    //如果获取到订单就删除，重新生成
                    iHpOrderService.delHpOrderById(pay.getOrdersNo());
                    Long createtime = idOrder.getCreatetime();
                    hpOrder.setCreatetime(createtime);
                }
                iHpOrderService.saveHpOrder(hpOrder,pay.getId(),pay.getUserId(),pay.getFrontIdCardUrl(),pay.getBackIdCardUrl(),pay.getContractUrl(),pay.getIdCard(),pay.getContactName(),pay.getPhoneNumber());
                iHpCustomerViewingRecordService.updateHpCustomerViewingRecordById(pay.getCustomerViewingRecordId());
            }else {
                ObOrder idOrder= iObOrderService.getByIdOrder(pay.getOrdersNo());
                if (idOrder!=null){
                    iObOrderService.delObOrderById(pay.getOrdersNo());
                    Long createtime = idOrder.getCreatetime();
                    obOrder.setCreatetime(createtime);
                }
                iObOrderService.saveObOrder(obOrder,pay.getId(),pay.getUserId(),pay.getFrontIdCardUrl(),pay.getBackIdCardUrl(),pay.getContractUrl(),pay.getIdCard(),pay.getContactName(),pay.getPhoneNumber());
                iObCustomerViewingRecordService.updateObCustomerViewingRecordById(pay.getCustomerViewingRecordId());
            }



            //前台传输一个openid--每个用户对应小程序都会生成一个独一无二的openid
            Map<String, Object> paraMap = new HashMap<>();
            //查询房屋
            String body = "cs";
            // 封装11个必需的参数
            //小程序ID
            paraMap.put("appid", environment.getProperty("wx.appId"));
            //商家ID
            paraMap.put("mch_id", environment.getProperty("wx.mchId"));
            //获取随机字符串 Nonce Str
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品名称
            paraMap.put("body", body);
            //订单号
            if (pay.getType()==MsgConsts.ZERO_STATUS) {
//                DecimalFormat df = new DecimalFormat("0.00%");
                NumberFormat numberFormat  = NumberFormat.getPercentInstance();
                queryWrapper.eq("id",pay.getId());
                HpInfo hpInfo = iHpInfoDetailService.getOne(queryWrapper);

                Double money = hpOrder.getTotalFee();//租金
                numberFormat.setMinimumFractionDigits(2);//保留到小数点后2位,不设置或者设置为0表示不保留小数
                String serviceFee = numberFormat.format(hpInfo.getServiceFee());//服务费
                String agencyFee = numberFormat.format(hpInfo.getAgencyFee());//中介费
                System.out.println("服务费"+serviceFee);
                System.out.println("中介费"+agencyFee);

                //支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
                String totalFee= BigDecimal.valueOf(money).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP) + "";

//                System.out.println("服务费"+(money+(money*serviceFee)));
//                System.out.println("中介费"+(agencyFee+(money*agencyFee)));


                paraMap.put("total_fee",totalFee);
                paraMap.put("out_trade_no", hpOrder.getOrderNo());
            }else{
                Double money = obOrder.getTotalFee();
                //支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
                String totalFee= BigDecimal.valueOf(money).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP) + "";

                paraMap.put("total_fee",totalFee);
                paraMap.put("out_trade_no", obOrder.getOrderNo());
            }
            paraMap.put("spbill_create_ip", ip);
            // 此路径是微信服务器调用支付结果通知路径
            paraMap.put("notify_url", "http://app-api.changsh.net/cre/app/payment/callBack");
            paraMap.put("trade_type", "JSAPI");
            paraMap.put("openid", pay.getOpenid());
            String sign = WXPayUtil.generateSignature(paraMap,  environment.getProperty("wx.mchKey"));
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
            log.info("xml=: "+xml);
            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = WXPayConstants.UNIFIEDORDER_URL;
            log.info("统一下单接口unifiedorder_url:"+unifiedorder_url);
            //发送post请求"统一下单接口"返回预支付id:prepay_id
            String xmlStr = HttpClientUtil.doPostXml(unifiedorder_url, xml);

            //以下内容是返回前端页面的json数据
            //预支付id
            String prepay_id = "";
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, Object> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
                //获取封装好的预支付id
                prepay_id =  map.get("prepay_id").toString();
                log.info("prepay_id_1=  "+prepay_id);
            }

            // 封装所需6个参数调支付页面
            Map<String, Object> payMap = new HashMap<String, Object>();
            payMap.put("appId", environment.getProperty("wx.appId"));
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");//获取当前时间戳，单位秒
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            //生成带有 sign 的 XML 格式字符串
            String paySign = WXPayUtil.generateSignature(payMap, environment.getProperty("wx.mchKey"));
            payMap.put("paySign", paySign);
            // 封装正常情况返回数据
            resMap.put("success",true);
            resMap.put("payMap",payMap);
        } catch (Exception e) {
            //异常删除订单

            // 封装异常情况返回数据
            log.info("调用统一订单接口错误");
            resMap.put("success",false);
            resMap.put("message","调用统一订单接口错误");
            e.printStackTrace();
        }
        return resMap;

    }




    /*支付成功回调*/
    @ApiOperation(httpMethod = "POST", value = "调用支付成功回调的地址")
    @RequestMapping(value = "callBack")
    public Result callBack(HttpServletRequest request, HttpServletResponse response){
        Result result = new Result();
        log.info("调用微信支付成功回调");
        InputStream is = null;
        String resXml = "";
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WXPayUtil.inputStream2String(is);
            Map<String, Object> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
            if(notifyMap.get("return_code").equals("SUCCESS")){
                try {
                    //获取到商户订单号
                    String ordersNo = notifyMap.get("out_trade_no").toString();//商户订单号
                    log.info("订单号"+ordersNo);
                    HpOrder hpOrder = iHpOrderService.getByIdOrder(ordersNo);
                    log.info("查询租房是否有该订单: "+hpOrder);
                    //如果查询出来有
                    if (hpOrder!=null) {
                        //商户订单号
                        iHpOrderService.updateByIdOrders(ordersNo);
                        log.info("订单状态修改成功");
                    }else{
                        ObOrder obOrder = iObOrderService.getByIdOrder(ordersNo);
                        log.info("查询写字楼是否有该订单: "+obOrder);
                        if (obOrder!=null) {
                            //商户订单号
                            iObOrderService.updateByIdOrders(ordersNo);
                            log.info("订单状态修改成功");
                        }
                    }
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();
                    System.err.println("返回给微信的值："+resXml.getBytes());
                    is.close();
                }catch (Exception e){
                    log.info("订单状态修改失败");
                    result.setMsg("订单状态修改失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

