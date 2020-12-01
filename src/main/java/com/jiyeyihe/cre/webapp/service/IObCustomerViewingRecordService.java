package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpCustomerViewingRecord;
import com.jiyeyihe.cre.webapp.entity.ObCustomerViewingRecord;

public interface IObCustomerViewingRecordService extends IService<ObCustomerViewingRecord> {

    /**
     * 写字楼客户看房记录分页查询
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @param businessId
     * @return
     */
    public String getObCustomerViewingRecordListPage(Long pageNum,Long pageSize,String propertyName,Long businessId);

    /**
     * 写字楼查询单个看房记录
     * @param id
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getObCustomerViewingRecordSelectById(Long id,Long businessId)throws Exception;

    /**
     * 写字楼
     * @param ObCustomerViewingRecordId
     * @return
     */
    public ObCustomerViewingRecord getByIdAppObCustomerViewingRecord(Long ObCustomerViewingRecordId);


    /**
     * app查询预约写字楼看房记录列表
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppObCustomerViewingRecordSelectListPage(Long pageNum, Long pageSize,Long userId,Integer viewingStatus) throws Exception;

    /**
     * app查询写字楼单个预约看房记录
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppObCustomerViewingRecordById(Long id,Long userId) throws Exception;

    /**
     * app查询写字楼单个预约看房记录
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppObOrderById(Long id,Long userId) throws Exception;


    /**
     * app租房 修改看房 点击支付 状态
     * @param obCustomerViewingRecordId
     * @return
     * @throws Exception
     */
    public int updateObCustomerViewingRecordById(Long obCustomerViewingRecordId)throws Exception;


    /**
     * 根据用户id查询 写字楼看房记录 是否有数据
     * @param userId
     * @return
     */
    int getByIdObUserCustomerViewingRecord(Long userId);



    /**
     * 删除租房 看房记录
     * @return
     */
    int delAppObCustomerViewingRecord(Long obCustomerViewingRecordId,Long obAppointmentHouseId);

}
