package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRent;
import com.jiyeyihe.cre.webapp.mapper.HpRentMapper;
import com.jiyeyihe.cre.webapp.service.IHpRentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class HpRentServiceImpl extends ServiceImpl<HpRentMapper, HpRent> implements IHpRentService {
    @Resource
    private HpRentMapper hpRentMapper;

    /**
     * 获取房屋详情表id，并修改租金表
     * @param hpInfo
     * @param hpRent
     * @return
     */
    @Override
    public int updateHpInfoDetailRentByLocationId(HpInfo hpInfo,HpRent hpRent) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        hpRent.setHpInfoId(hpInfo.getId());
        updateWrapper.eq("hp_info_id",hpRent.getHpInfoId());
        int update = hpRentMapper.update(hpRent, updateWrapper);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delHpRentById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("hp_info_id",id);
        int delete = hpRentMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }
}
