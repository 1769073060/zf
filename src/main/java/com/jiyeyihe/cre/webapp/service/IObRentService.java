package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRent;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObRent;

public interface IObRentService extends IService<ObRent> {

    public int updateObInfoDetailRentByLocationId(ObInfo obInfo, ObRent obRent);

    /**
     * 根据 obInfoId删除 租金内容
     * @param id
     * @return
     */
    public int delObRentById(Long id);

}
