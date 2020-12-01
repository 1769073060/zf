package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpCustomerViewingRecord;
import com.jiyeyihe.cre.webapp.entity.HpCustomerViewingRecordVo;
import com.jiyeyihe.cre.webapp.entity.HpResources;

public interface IHpCustomerViewingRecordService extends IService<HpCustomerViewingRecord> {

    /**
     * 客户看房记录分页查询
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @param businessId
     * @return
     */
    public String getHpCustomerViewingRecordListPage(Long pageNum,Long pageSize,String communityName,Long businessId);

    /**
     * 查询单个看房记录
     * @param id
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getHpCustomerViewingRecordSelectById(Long id,Long businessId)throws Exception;

    /**
     * 查询租房看房状态
     * @param HpCustomerViewingRecordId
     * @return
     */
    public HpCustomerViewingRecord getByIdAppHpCustomerViewingRecord(Long HpCustomerViewingRecordId);

    /**
     * app查询预约看房记录列表
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppHpCustomerViewingRecordSelectListPage(Long pageNum, Long pageSize,Long userId,Integer viewingStatus) throws Exception;

    /**
     * app查询单个预约看房记录
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppHpCustomerViewingRecordById(Long id,Long userId) throws Exception;


    /**
     * app查询单个预约看房订单记录
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppHpOrderById(Long id,Long userId) throws Exception;



    /**
     * app租房 修改看房 点击支付 状态
     * @param obCustomerViewingRecordId
     * @return
     * @throws Exception
     */
    public int updateHpCustomerViewingRecordById(Long obCustomerViewingRecordId)throws Exception;

    /**
     * 根据用户id查询 租房看房记录 是否有数据
     * @param userId
     * @return
     */
    int getByIdHpUserCustomerViewingRecord(Long userId);

    /**
     * 删除租房 看房记录
     * @return
     */
    int delAppHpCustomerViewingRecord(Long hpCustomerViewingRecordId,Long hpAppointmentHouseId);
}
