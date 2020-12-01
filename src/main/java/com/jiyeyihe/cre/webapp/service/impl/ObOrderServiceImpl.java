package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.WXPay.WXPayUtil;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.mapper.ObOrderMapper;
import com.jiyeyihe.cre.webapp.service.IObInfoDetailService;
import com.jiyeyihe.cre.webapp.service.IObOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
@Transactional
public class ObOrderServiceImpl extends ServiceImpl<ObOrderMapper, ObOrder> implements IObOrderService {
    @Resource
    private IObInfoDetailService iObInfoDetailService;
    @Resource
    private ObOrderMapper obOrderMapper;
    @Resource
    private FileUtils fileUtils;


    @Override
    public int saveObOrder(ObOrder obOrder,Long id,Long userId,String frontIdCardUrl,String backIdCardUrl,String contractUrl,String idCard,String contactName,String phoneNumber) {
        ObInfo selectById = iObInfoDetailService.getAppObInfoOrderSelectById(id);
        //Double money = selectById.getRentalMonth()
        //插入一条订单数据
        obOrder.setOrderNo(WXPayUtil.generateNonceStr());//生成订单号
        obOrder.setUserId(userId);//标记用户
        obOrder.setOrderStatus(MsgConsts.UNPAID);//生成订单未支付
        obOrder.setBusinessId(MsgConsts.BusinessId_Status);
        obOrder.setPaymentType(MsgConsts.ZERO_STATUS);//是否点击支付
        obOrder.setTotalFee(selectById.getRentalMonth());
        obOrder.setObInfoId(id);//获取房屋id
        if (obOrder.getCreatetime()==null){
            obOrder.setCreatetime(System.currentTimeMillis());
        }
        obOrder.setUpdatetime(System.currentTimeMillis());
        obOrder.setClickStatus(MsgConsts.NO_CLICK_RAID);
        obOrder.setFrontIdCardUrl(frontIdCardUrl);
        obOrder.setBackIdCardUrl(backIdCardUrl);
        obOrder.setContractUrl(contractUrl);
        obOrder.setIdCard(idCard);
        obOrder.setContactName(contactName);
        obOrder.setPhoneNumber(phoneNumber);
        int insert = obOrderMapper.insert(obOrder);
        if (insert>0){
            return MsgConsts.SUCCESS_CODE;
        }else{
            return MsgConsts.FAIL_CODE;
        }
    }

    @Override
    public ObOrder getByIdOrder(String ordersNo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no",ordersNo);
        return obOrderMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateByIdOrders(String orderNo) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        ObOrder obOrder = new ObOrder();
        //订单已点击支付
        obOrder.setClickStatus(MsgConsts.CLICK_RAID);
        //订单已支付状态
        obOrder.setOrderStatus(MsgConsts.RAID);
        updateWrapper.eq("order_no",orderNo);
        int update = obOrderMapper.update(obOrder, updateWrapper);
        if (update>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }
        return MsgConsts.FAIL_CODE;
    }

    @Override
    public String getAppObOrderSelectListPage(Long pageNum, Long pageSize, String orderNo, Long userId, Integer orderStatus) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObOrderVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObOrderVo> queryWrapper = new QueryWrapper<ObOrderVo>();
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 170).ge("sd2.id", 169);
        queryWrapper.le("sd3.id", 176).ge("sd3.id", 171);
        queryWrapper.le("sd4.id", 181).ge("sd4.id", 180);
        queryWrapper.le("sd5.id", 161).ge("sd5.id", 159);
        queryWrapper.le("sd6.id", 194).ge("sd6.id", 193);
        queryWrapper.le("sd7.id", 196).ge("sd7.id", 195);
        if (orderNo != null || "".equals(orderNo)) {
            queryWrapper.like("oo.order_no", orderNo);
        }
        if (userId != null || "".equals(userId)) {
            queryWrapper.eq("oo.user_id", userId);
            queryWrapper.eq("ocvr.user_id", userId);
        }
        queryWrapper.eq("oo.order_status", orderStatus);// 根据状态查询
        List<ObOrderVo> roleList = obOrderMapper.getAppObOrderSelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("oo.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obOrderMapper.getAppObOrderSelectListPage(queryWrapper);
        List<ObOrderVo> arrayList = new ArrayList<>();
        for (ObOrderVo obOrderVo : roleList) {
            if (!isEmpty(obOrderVo.getCarouselUrl())) {
                obOrderVo.setCarouselUrl(fileUtils.getManyUrl(obOrderVo.getCarouselUrl()));
                String carouselUrl = obOrderVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                obOrderVo.setCarouselUrl(substring);
            }
            arrayList.add(obOrderVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public int delObOrderById(String orderNo) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_no",orderNo);
        int delete = obOrderMapper.delete(updateWrapper);
        if (delete>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }
        return MsgConsts.FAIL_CODE;
    }

    @Override
    public String getIdAppObOrderDetail(Long obInfoId,String orderNo) {
        JSONObject object = new JSONObject();
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 176).ge("sd1.id", 171);
        queryWrapper.le("sd2.id", 181).ge("sd2.id", 180);
        queryWrapper.le("sd3.id", 123).ge("sd3.id", 120);
        queryWrapper.le("sd4.id", 194).ge("sd4.id", 193);
        queryWrapper.le("sd5.id", 179).ge("sd5.id", 177);
        queryWrapper.eq("oo.ob_info_id", obInfoId);
        queryWrapper.eq("oo.order_no", orderNo);
        Map<String, Object> selectById = obOrderMapper.getIdAppObOrderDetail(queryWrapper);
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public int getByIdObUserOrder(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List obOrder = obOrderMapper.selectList(queryWrapper);
        if (obOrder.size()!=0){
            return MsgConsts.FIRST_STATUS;
        }return MsgConsts.ZERO_STATUS;
    }
}
