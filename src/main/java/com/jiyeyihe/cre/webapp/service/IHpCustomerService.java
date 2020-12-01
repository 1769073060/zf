package com.jiyeyihe.cre.webapp.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpCustomerService;
import org.apache.ibatis.annotations.Param;

public interface IHpCustomerService extends IService<HpCustomerService> {

    /**
     * 获取房产客服房屋名称下拉框
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @param businessId
     * @return
     */
    public String getHpCustomerSelectList(Long pageNum, Long pageSize, String communityName,Long businessId);

    /**
     * 房产客服分页查询
     * @param pageNum
     * @param pageSize
     * @param houseName
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getHpCustomerListPage(Long pageNum, Long pageSize, String houseName,Long businessId) throws Exception ;

    public HpCustomerService getHpCustomerSelectById(@Param("id")Long id)throws Exception;

    /**
     * 查询单个房产客服配置
     * @param id
     * @return
     * @throws Exception
     */
    public String getHpCustomersSelectById(@Param("id")Long id)throws Exception;
}
