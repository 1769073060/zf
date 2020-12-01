package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.ObCustomerService;
import org.apache.ibatis.annotations.Param;

public interface IObCustomerServiceService extends IService<ObCustomerService> {

    /**
     * 获取写字楼房产客服房屋名称下拉框
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @param businessId
     * @return
     */
    public String getObCustomerSelectList(Long pageNum, Long pageSize, String propertyName,Long businessId);

    /**
     * 写字楼房产客服列表展示
     * @param pageNum
     * @param pageSize
     * @param houseName
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getObCustomerListPage(Long pageNum, Long pageSize, String houseName,Long businessId) throws Exception ;


    /**
     * 查询单个写字楼房产客服配置
     * @param id
     * @return
     * @throws Exception
     */
    public String getObCustomersSelectById(@Param("id")Long id)throws Exception;

}
