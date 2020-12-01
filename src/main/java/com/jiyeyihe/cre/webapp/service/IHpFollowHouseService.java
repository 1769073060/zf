package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;

public interface IHpFollowHouseService extends IService<HpFollowHouse> {


    /**
     * app修改房源关注
     * @param id
     * @param userId
     * @return
     */
    public HpFollowHouse getAppHpResourceFollowById(Long id,Long userId);


    /**
     * 根据用户id查询 租房关注 是否有数据
     * @param userId
     * @return
     */
    int getByIdHpUserFollow(Long userId);
}
