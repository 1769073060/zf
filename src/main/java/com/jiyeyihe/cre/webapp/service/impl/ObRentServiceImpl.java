package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRent;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObRent;
import com.jiyeyihe.cre.webapp.mapper.ObRentMapper;
import com.jiyeyihe.cre.webapp.service.IObRentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ObRentServiceImpl extends ServiceImpl<ObRentMapper, ObRent> implements IObRentService {

    @Resource
    private ObRentMapper obRentMapper;

    @Override
    public int updateObInfoDetailRentByLocationId(ObInfo obInfo, ObRent obRent) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        obRent.setObInfoId(obInfo.getId());
        updateWrapper.eq("ob_info_id",obRent.getObInfoId());
        int update = obRentMapper.update(obRent, updateWrapper);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delObRentById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ob_info_id",id);
        int delete = obRentMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }
}
