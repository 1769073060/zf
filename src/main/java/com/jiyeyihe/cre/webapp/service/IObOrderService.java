package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpOrder;
import com.jiyeyihe.cre.webapp.entity.ObOrder;

import java.util.Map;

public interface IObOrderService extends IService<ObOrder> {


    /**
     * 插入写字楼订单
     * @param id
     * @param userId
     * @return
     */
    int saveObOrder(ObOrder obOrder,Long id,Long userId,String frontIdCardUrl,String backIdCardUrl,String contractUrl,String idCard,String contactName,String phoneNumber);

    /**
     * 查询写字楼订单详细
     * @param ordersNo
     * @return
     */
    ObOrder getByIdOrder(String ordersNo);

    /**
     * 修改写字楼状态
     * @param orderNo
     * @return
     */
    int updateByIdOrders(String orderNo);

    /**
     *  查询写字楼订单列表
     * @param pageNum
     * @param pageSize
     * @param orderNo
     * @param userId
     * @param orderStatus
     * @return
     */
    String getAppObOrderSelectListPage(Long pageNum,Long pageSize,String orderNo,Long userId,Integer orderStatus) throws Exception;

    /**
     * 根据订单号 删除写字楼订单
     * @param orderNo
     * @return
     */
    int delObOrderById(String orderNo);


    /**
     * 查询写字楼单个订单详情
     * @param obInfoId
     * @return
     */
    String getIdAppObOrderDetail(Long obInfoId,String orderNo);


    /**
     * 根据用户id查询写字楼是否有数据
     * @param userId
     * @return
     */
    int getByIdObUserOrder(Long userId);
}
