package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.mapper.ObMatchingMapper;
import com.jiyeyihe.cre.webapp.service.IObMatchingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ObMatchingServiceImpl extends ServiceImpl<ObMatchingMapper, ObMatching> implements IObMatchingService {

    @Resource
    private ObMatchingMapper obMatchingMapper;

    @Override
    public int updateObInfoDetailMatchingByLocationId(ObInfo obInfo, ObMatching obMatching) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        obMatching.setObInfoId(obInfo.getId());
        updateWrapper.eq("ob_info_id",obMatching.getObInfoId());
        int update = obMatchingMapper.update(obMatching, updateWrapper);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delObMatchingById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ob_info_id",id);
        int delete = obMatchingMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }
}
