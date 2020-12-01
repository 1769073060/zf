package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRent;


public interface IHpRentService extends IService<HpRent> {

    public int updateHpInfoDetailRentByLocationId(HpInfo hpInfo,HpRent hpRent);

    public int delHpRentById(Long id);
}
