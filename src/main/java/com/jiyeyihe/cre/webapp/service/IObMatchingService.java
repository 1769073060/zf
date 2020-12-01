package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpFurniture;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObMatching;

public interface IObMatchingService extends IService<ObMatching> {

    public int updateObInfoDetailMatchingByLocationId(ObInfo obInfo, ObMatching obMatching) throws Exception;

    /**
     * 根据 obInfoId删除 写字楼配置
     * @param id
     * @return
     */
    public int delObMatchingById(Long id);
}
