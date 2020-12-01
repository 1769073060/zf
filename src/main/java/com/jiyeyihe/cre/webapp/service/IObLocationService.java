package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.ObLocation;
import org.apache.ibatis.annotations.Param;

public interface IObLocationService extends IService<ObLocation> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @return
     * @throws Exception
     */
    public String getObLocationListPage(Long pageNum,Long pageSize,String propertyName,Long businessId) throws Exception;


    /**
     * 查询单个写字楼区域信息
     * @param id
     * @return
     */
    public String getObLocationSelectById(@Param("id")Long id, @Param("businessId")Long businessId)throws Exception;

}
