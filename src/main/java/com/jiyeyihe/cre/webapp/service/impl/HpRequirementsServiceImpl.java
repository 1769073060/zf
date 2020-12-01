package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRequirements;
import com.jiyeyihe.cre.webapp.mapper.HpRequirementsMapper;
import com.jiyeyihe.cre.webapp.service.IHpRequirementsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class HpRequirementsServiceImpl extends ServiceImpl<HpRequirementsMapper, HpRequirements> implements IHpRequirementsService {

    @Resource
    private HpRequirementsMapper hpRequirementsMapper;

    /**
     * 获取房屋详情表id，并修改出租要求表
     * @param hpInfo
     * @param hpRequirements
     * @return
     */
    @Override
    public int updateHpInfoDetailRequirementsByLocationId(HpInfo hpInfo,HpRequirements hpRequirements) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        hpRequirements.setHpInfoId(hpInfo.getId());
        updateWrapper.eq("hp_info_id",hpRequirements.getHpInfoId());
        int update = hpRequirementsMapper.update(hpRequirements, updateWrapper);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delHpRequirementsById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("hp_info_id",id);
        int delete = hpRequirementsMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }
}
