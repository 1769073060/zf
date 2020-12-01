package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRequirements;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObSurroundingFacilities;

public interface IObSurroundingFacilitiesService extends IService<ObSurroundingFacilities> {

    public int updateObInfoDetailSurroundingFacilitiesByLocationId(ObInfo ObInfo, ObSurroundingFacilities ObSurroundingFacilities);

    /**
     * 根据 obInfoId删除 写字楼周边配套
     * @param id
     * @return
     */
    public int delObSurroundingFacilitiesById(Long id);

}
