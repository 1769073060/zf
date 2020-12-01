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
import com.jiyeyihe.cre.webapp.mapper.HpOrderMapper;
import com.jiyeyihe.cre.webapp.mapper.ObOrderMapper;
import com.jiyeyihe.cre.webapp.service.IHpInfoDetailService;
import com.jiyeyihe.cre.webapp.service.IHpOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Service
@Transactional
public class HpOrderServiceImpl extends ServiceImpl<HpOrderMapper, HpOrder> implements IHpOrderService {

    @Resource
    private HpOrderMapper hpOrderMapper;
    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private FileUtils fileUtils;


    @Override
    public HpOrder getByIdOrder(String ordersNo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no",ordersNo);
        return hpOrderMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateByIdOrders(String orderNo) {
        UpdateWrapper<HpOrder> updateWrapper = new UpdateWrapper<>();
        HpOrder hpOrder = new HpOrder();
        //订单已点击支付
        hpOrder.setClickStatus(MsgConsts.CLICK_RAID);
        //订单已支付状态
        hpOrder.setOrderStatus(MsgConsts.RAID);
        updateWrapper.eq("order_no",orderNo);
        int update = hpOrderMapper.update(hpOrder,updateWrapper);
        if (update>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }
        return 0;
    }

    @Override
    public String getAppHpOrderSelectListPage(Long pageNum, Long pageSize, String orderNo,Long userId,Integer orderStatus) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpOrderVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpOrderVo> queryWrapper = new QueryWrapper<HpOrderVo>();
        queryWrapper.le("sd1.id", 119).ge("sd1.id", 118);
        queryWrapper.le("sd2.id", 168).ge("sd2.id", 164);
        queryWrapper.le("sd3.id", 123).ge("sd3.id", 120);
        queryWrapper.le("sd4.id", 133).ge("sd4.id", 124);
        if (orderNo != null || "".equals(orderNo)) {
            queryWrapper.like("ho.order_no", orderNo);
        }
        if (userId != null || "".equals(userId)) {
            queryWrapper.eq("ho.user_id", userId);
            queryWrapper.eq("hcvr.user_id", userId);
        }

        queryWrapper.eq("ho.order_status", orderStatus);// 根据状态查询

        List<HpOrderVo> roleList = hpOrderMapper.getAppHpOrderSelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("ho.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpOrderMapper.getAppHpOrderSelectListPage(queryWrapper);
        List<HpOrderVo> arrayList = new ArrayList<>();
        for (HpOrderVo hpOrderVo : roleList) {
            if (!isEmpty(hpOrderVo.getCarouselUrl())) {
                hpOrderVo.setCarouselUrl(fileUtils.getManyUrl(hpOrderVo.getCarouselUrl()));
                String carouselUrl = hpOrderVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                hpOrderVo.setCarouselUrl(substring);
            }
            arrayList.add(hpOrderVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public int saveHpOrder(HpOrder hpOrder,Long id,Long userId,
                           String frontIdCardUrl,String backIdCardUrl,
                           String contractUrl,String idCard,
                           String contactName,String phoneNumber) {
        HpInfo selectById = iHpInfoDetailService.getAppHpInfoOrderSelectById(id);
        //Double money = selectById.getRental();
        //插入一条订单数据
        hpOrder.setOrderNo(WXPayUtil.generateNonceStr());//生成订单号
        hpOrder.setUserId(userId);//标记用户
        hpOrder.setOrderStatus(MsgConsts.UNPAID);//未支付
        hpOrder.setBusinessId(MsgConsts.BusinessId_Status);
        hpOrder.setPaymentType(MsgConsts.ZERO_STATUS);
        hpOrder.setTotalFee(selectById.getRental());
        hpOrder.setHpInfoId(id);//获取房屋id
        if (hpOrder.getCreatetime()==null){
            hpOrder.setCreatetime(System.currentTimeMillis());
        }
        hpOrder.setUpdatetime(System.currentTimeMillis());
        hpOrder.setClickStatus(MsgConsts.NO_CLICK_RAID);
        hpOrder.setFrontIdCardUrl(frontIdCardUrl);
        hpOrder.setBackIdCardUrl(backIdCardUrl);
        hpOrder.setContractUrl(contractUrl);
        hpOrder.setIdCard(idCard);
        hpOrder.setContactName(contactName);
        hpOrder.setPhoneNumber(phoneNumber);
        int insert = hpOrderMapper.insert(hpOrder);
        if (insert>0){
            return MsgConsts.SUCCESS_CODE;
        }else{
            return MsgConsts.FAIL_CODE;
        }
    }

    @Override
    public int delHpOrderById(String orderNo) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_no",orderNo);
        int delete = hpOrderMapper.delete(updateWrapper);
        if (delete>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }
        return MsgConsts.FAIL_CODE;
    }

    @Override
    public String getIdAppHpOrderDetail(Long hpInfoId, String orderNo) {
        JSONObject object = new JSONObject();
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 119).ge("sd1.id", 118);
        queryWrapper.le("sd2.id", 168).ge("sd2.id", 164);
        queryWrapper.le("sd3.id", 123).ge("sd3.id", 120);
        queryWrapper.le("sd4.id", 139).ge("sd4.id", 134);
        queryWrapper.eq("ho.hp_info_id", hpInfoId);
        queryWrapper.eq("ho.order_no", orderNo);
        Map<String, Object> selectById = hpOrderMapper.getIdAppHpOrderDetail(queryWrapper);
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public int getByIdHpUserOrder(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List hpOrder = hpOrderMapper.selectList(queryWrapper);
        if (hpOrder.size()!=0){
            return MsgConsts.FIRST_STATUS;
        }return MsgConsts.ZERO_STATUS;
    }
}
