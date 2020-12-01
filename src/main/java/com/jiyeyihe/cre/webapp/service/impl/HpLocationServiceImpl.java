package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jiyeyihe.cre.webapp.entity.Dictionary;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.entity.HpLocationVo;
import com.jiyeyihe.cre.webapp.mapper.HpLocationMapper;

import com.jiyeyihe.cre.webapp.service.IHpLocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class HpLocationServiceImpl extends ServiceImpl<HpLocationMapper, HpLocation> implements IHpLocationService {
    @Resource
    private HpLocationMapper hpLocationMapper;

    @Override
    public String getHpLocationListPage(Long pageNum,Long pageSize,String communityName,Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpLocationVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (communityName!=null||"".equals(communityName)){
            queryWrapper.like("community_name",communityName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hl.business_id",businessId);
        }
        List<HpLocationVo> roleList = hpLocationMapper.getHpLocationSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpLocationMapper.getHpLocationSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getHpLocationSelectById(Long id,Long businessId) throws Exception{
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hl.id",id);
        queryWrapper.eq("hl.business_id",businessId);
        Map<String,Object> map  = hpLocationMapper.getHpLocationSelectById(queryWrapper);
        object.put("dataList",map);
        return object.toString();
    }


}
