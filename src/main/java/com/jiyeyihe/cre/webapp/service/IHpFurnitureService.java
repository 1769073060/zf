package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpFurniture;
import com.jiyeyihe.cre.webapp.entity.HpInfo;


public interface IHpFurnitureService extends IService<HpFurniture> {

    public int updateHpInfoDetailFurnitureByLocationId(HpInfo hpInfo,HpFurniture hpFurniture) throws Exception;

    public int delHpFurnitureById(Long id);
}
