package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.ObInfoVo;
import com.jiyeyihe.cre.webapp.entity.ObResources;
import com.jiyeyihe.cre.webapp.mapper.ObResourcesMapper;
import com.jiyeyihe.cre.webapp.service.IObResourcesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ObResourcesServiceImpl extends ServiceImpl<ObResourcesMapper, ObResources> implements IObResourcesService {

    @Resource
    private ObResourcesMapper obResourcesMapper;

    @Override
    public String getObResourceSelectListPage(Long pageNum, Long pageSize, String propertyName, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<ObInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        if (propertyName!=null||"".equals(propertyName)){
            queryWrapper.like("hi.property_name",propertyName).or().like("hi.house_number",propertyName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }

        List<ObInfoVo> roleList = obResourcesMapper.getObResourceSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obResourcesMapper.getObResourceSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);

        return object.toString();

    }

    @Override
    public String getObResourcesSelectList(Long pageNum, Long pageSize, String propertyName, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<ObResources> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObResources> queryWrapper = new QueryWrapper<ObResources>();
        if (propertyName!=null||"".equals(propertyName)){
            queryWrapper.like("hi.property_name",propertyName).or().like("hi.house_number",propertyName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        queryWrapper.notInSql("hi.id","select hr.ob_info_id from t_ob_resources hr");
        List<ObResources> selectList = obResourcesMapper.getObResourcesSelectList(queryWrapper);
        page.setTotal(selectList.size());
        page.setPages(selectList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) *(pageSize) + " ," + pageSize);
        selectList = obResourcesMapper.getObResourcesSelectList(queryWrapper);
        page.setRecords(selectList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",selectList);
        return object.toString();
    }

    @Override
    public int delObResourcesById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ob_info_id",id);
        int delete = obResourcesMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }

    @Override
    public ObResources getObResourceById(Long obInfoId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hs.ob_info_id",obInfoId);
        return obResourcesMapper.getObResourceById(queryWrapper);
    }
}
