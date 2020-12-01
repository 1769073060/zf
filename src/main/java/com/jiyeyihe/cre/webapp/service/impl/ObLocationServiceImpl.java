package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.ObLocation;
import com.jiyeyihe.cre.webapp.entity.ObLocationVo;
import com.jiyeyihe.cre.webapp.mapper.ObLocationMapper;
import com.jiyeyihe.cre.webapp.service.IObLocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ObLocationServiceImpl extends ServiceImpl<ObLocationMapper, ObLocation> implements IObLocationService {

    @Resource
    private ObLocationMapper obLocationMapper;

    @Override
    public String getObLocationListPage(Long pageNum, Long pageSize, String propertyName, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObLocationVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (propertyName!=null||"".equals(propertyName)){
            queryWrapper.like("property_name",propertyName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hl.business_id",businessId);
        }
        List<ObLocationVo> roleList = obLocationMapper.getObLocationSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obLocationMapper.getObLocationSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getObLocationSelectById(Long id, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hl.id",id);
        queryWrapper.eq("hl.business_id",businessId);
        Map<String,Object> map  = obLocationMapper.getObLocationSelectById(queryWrapper);
        object.put("dataList",map);
        return object.toString();
    }
}
