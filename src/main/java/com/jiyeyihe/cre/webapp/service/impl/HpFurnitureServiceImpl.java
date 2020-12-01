package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jiyeyihe.cre.webapp.entity.HpFurniture;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.mapper.HpFurnitureMapper;

import com.jiyeyihe.cre.webapp.service.IHpFurnitureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class HpFurnitureServiceImpl extends ServiceImpl<HpFurnitureMapper, HpFurniture> implements IHpFurnitureService {
    @Resource
    private HpFurnitureMapper hpFurnitureMapper;

    /**
     * 获取房屋详情表id，修改家具表
     * @param hpInfo
     * @param hpFurniture
     * @return
     * @throws Exception
     */
    @Override
    public int updateHpInfoDetailFurnitureByLocationId(HpInfo hpInfo,HpFurniture hpFurniture) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        hpFurniture.setHpInfoId(hpInfo.getId());
        updateWrapper.eq("hp_info_id",hpFurniture.getHpInfoId());
        int update = hpFurnitureMapper.update(hpFurniture, updateWrapper);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delHpFurnitureById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("hp_info_id",id);
        int delete = hpFurnitureMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }
}
