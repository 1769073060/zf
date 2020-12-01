package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpOrder;

public interface IHpOrderService extends IService<HpOrder> {



    /**
     * 获取租房单个订单详情
     * @param id
     * @return
     */
    HpOrder getByIdOrder(String id);

    /**
     * 修改租房订单状态
     * @param orderNo
     * @return
     */
    int updateByIdOrders(String orderNo);

    /**
     * 根据订单状态 分页查询租房订单列表
     * @param pageNum
     * @param pageSize
     * @param orderNo
     * @param userId
     * @param orderStatus
     * @return
     */
    String getAppHpOrderSelectListPage(Long pageNum,Long pageSize,String orderNo,Long userId,Integer orderStatus) throws Exception;

    /**
     * 插入租房订单
     * @param id
     * @param userId
     * @return
     */
    int saveHpOrder(HpOrder hpOrder,Long id,Long userId,String frontIdCardUrl,String backIdCardUrl,String contractUrl,String idCard,String contactName,String phoneNumber);



    /**
     * 根据订单号 删除写字楼订单
     * @param orderNo
     * @return
     */
    int delHpOrderById(String orderNo);


    /**
     * 查询租房单个订单
     * @param hpInfoId
     * @return
     */
    String getIdAppHpOrderDetail(Long hpInfoId,String orderNo);


    /**
     * 根据用户id查询租房是否有数据
     * @param userId
     * @return
     */
    int getByIdHpUserOrder(Long userId);
}
