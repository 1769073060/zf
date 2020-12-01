package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpResources;
import com.jiyeyihe.cre.webapp.entity.ObResources;

public interface IObResourcesService extends IService<ObResources> {

    /**
     * 写字楼信息查询列表展示
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @param businessId
     * @return
     */
    public String getObResourceSelectListPage(Long pageNum,Long pageSize,String propertyName,Long businessId);

    /**
     * 获取新增发布写字楼里面的弹出框
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @param businessId
     * @return
     */
    public String getObResourcesSelectList(Long pageNum, Long pageSize, String propertyName,Long businessId);

    /**
     * 删除我发布的房源
     * @param id
     * @return
     */
    public int delObResourcesById(Long id);

    public ObResources getObResourceById(Long obInfoId);

}
