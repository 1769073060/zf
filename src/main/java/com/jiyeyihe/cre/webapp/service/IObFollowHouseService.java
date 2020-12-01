package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;
import com.jiyeyihe.cre.webapp.entity.ObFollowHouse;

public interface IObFollowHouseService extends IService<ObFollowHouse> {


    /**
     * app修改写字楼关注
     * @param id
     * @param userId
     * @return
     */
    public ObFollowHouse getAppObResourceFollowById(Long id, Long userId);


    /**
     * 根据用户id查询 写字楼关注 是否有数据
     * @param userId
     * @return
     */
    public int getByIdObUserFollow(Long userId);
}
