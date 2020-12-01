package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;
import com.jiyeyihe.cre.webapp.entity.ObFollowHouse;
import com.jiyeyihe.cre.webapp.mapper.HpFollowHouseMapper;
import com.jiyeyihe.cre.webapp.mapper.ObFollowHouseMapper;
import com.jiyeyihe.cre.webapp.service.IObFollowHouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class ObFollowHouseServiceImpl extends ServiceImpl<ObFollowHouseMapper, ObFollowHouse> implements IObFollowHouseService {
    @Resource
    private ObFollowHouseMapper obFollowHouseMapper;

    @Override
    public ObFollowHouse getAppObResourceFollowById(Long id, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hfh.ob_info_id", id);
        queryWrapper.eq("hfh.user_id", userId);
        return obFollowHouseMapper.getAppObResourceFollow(queryWrapper);
    }

    @Override
    public int getByIdObUserFollow(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("attention_status",  MsgConsts.FIRST_STATUS);
        List obFollow = obFollowHouseMapper.selectList(queryWrapper);
        if (obFollow.size()!=0){
            return MsgConsts.FIRST_STATUS;
        }return MsgConsts.ZERO_STATUS;
    }
}
