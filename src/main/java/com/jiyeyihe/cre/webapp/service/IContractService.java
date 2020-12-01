package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.Contract;
import com.jiyeyihe.cre.webapp.entity.ObInfo;

public interface IContractService extends IService<Contract> {

    /**
     * 查询租房单个合同模板
     * @param id
     * @return
     */
    String getIdContract(Long id);

    /**
     * 查询租房合同模板列表
     * @param pageNum
     * @param pageSize
     * @param contractTemplateName
     * @param businessId
     * @return
     */
    String getContractListPage(Long pageNum,Long pageSize,String contractTemplateName,Long businessId);


    /**
     * app查询租房合同模板
     * @param pageNum
     * @param pageSize
     * @param businessId
     * @return
     */
    String getAppContractSelectListPage(Long pageNum,Long pageSize,Long businessId);

    /**
     * 查詢最大id
     * @return
     */
    public Contract listMaxId();

}
