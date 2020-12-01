package com.jiyeyihe.cre.webapp.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObSurroundingFacilities;
import com.jiyeyihe.cre.webapp.mapper.ObSurroundingFacilitiesMapper;
import com.jiyeyihe.cre.webapp.service.IObSurroundingFacilitiesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ObSurroundingFacilitiesServiceImpl extends ServiceImpl<ObSurroundingFacilitiesMapper, ObSurroundingFacilities> implements IObSurroundingFacilitiesService {

    @Resource
    private ObSurroundingFacilitiesMapper obSurroundingFacilitiesMapper;

    @Override
    public int updateObInfoDetailSurroundingFacilitiesByLocationId(ObInfo ObInfo, ObSurroundingFacilities ObSurroundingFacilities) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        ObSurroundingFacilities.setObInfoId(ObInfo.getId());
        updateWrapper.eq("ob_info_id",ObSurroundingFacilities.getObInfoId());
        int update = obSurroundingFacilitiesMapper.update(ObSurroundingFacilities, updateWrapper);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delObSurroundingFacilitiesById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ob_info_id",id);
        int delete = obSurroundingFacilitiesMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }




}
