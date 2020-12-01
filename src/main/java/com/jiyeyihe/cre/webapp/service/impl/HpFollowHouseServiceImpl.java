package com.jiyeyihe.cre.webapp.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;
import com.jiyeyihe.cre.webapp.mapper.HpFollowHouseMapper;
import com.jiyeyihe.cre.webapp.service.IHpFollowHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HpFollowHouseServiceImpl extends ServiceImpl<HpFollowHouseMapper, HpFollowHouse> implements IHpFollowHouseService {

    @Resource
    private HpFollowHouseMapper hpFollowHouseMapper;

    @Override
    public HpFollowHouse getAppHpResourceFollowById(Long id,Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hfh.hp_info_id", id);
        queryWrapper.eq("hfh.user_id", userId);
        return hpFollowHouseMapper.getAppHpResourceFollow(queryWrapper);
    }

    @Override
    public int getByIdHpUserFollow(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("attention_status", MsgConsts.FIRST_STATUS);
        List hpFollow = hpFollowHouseMapper.selectList(queryWrapper);
        System.out.println(hpFollow);
        if (hpFollow.size()!=0){
            return MsgConsts.FIRST_STATUS;
        }return MsgConsts.ZERO_STATUS;
    }
}
