package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.entity.HpLocationVo;
import org.apache.ibatis.annotations.Param;


public interface IHpLocationService extends IService<HpLocation> {

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @return
     * @throws Exception
     */
    public String getHpLocationListPage(Long pageNum,Long pageSize,String communityName,Long businessId) throws Exception;


    /**
     * 查询单个id
     * @param id
     * @return
     */
    public String getHpLocationSelectById(@Param("id")Long id,@Param("businessId")Long businessId)throws Exception;
}
