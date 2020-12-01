package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.HpCustomerServiceVo;
import com.jiyeyihe.cre.webapp.entity.ObCustomerService;
import com.jiyeyihe.cre.webapp.entity.ObCustomerServiceVo;
import com.jiyeyihe.cre.webapp.mapper.ObCustomerServiceMapper;
import com.jiyeyihe.cre.webapp.service.IObCustomerServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ObCustomerServiceServiceImpl extends ServiceImpl<ObCustomerServiceMapper, ObCustomerService> implements IObCustomerServiceService {

    @Resource
    private ObCustomerServiceMapper obCustomerServiceMapper;

    @Override
    public String getObCustomerSelectList(Long pageNum, Long pageSize, String propertyName, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<ObCustomerServiceVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObCustomerServiceVo> queryWrapper = new QueryWrapper<ObCustomerServiceVo>();
        if (propertyName!=null||"".equals(propertyName)){
            queryWrapper.like("hi.property_name",propertyName).or().like("hi.house_number",propertyName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        List<ObCustomerServiceVo> selectList = obCustomerServiceMapper.getObCustomerSelectList(queryWrapper);
        page.setTotal(selectList.size());
        page.setPages(selectList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        selectList = obCustomerServiceMapper.getObCustomerSelectList(queryWrapper);
        page.setRecords(selectList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",selectList);
        return object.toString();
    }

    @Override
    public String getObCustomerListPage(Long pageNum, Long pageSize, String houseName, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObCustomerServiceVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (houseName!=null||"".equals(houseName)){
            queryWrapper.like("house_name",houseName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hcs.business_id",businessId);
        }
        List<ObCustomerServiceVo> roleList = obCustomerServiceMapper.getObCustomerListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hcs.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obCustomerServiceMapper.getObCustomerListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getObCustomersSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hcs.id",id);
        Map<String,Object> map  = obCustomerServiceMapper.getObCustomersSelectById(queryWrapper);
        object.put("dataList",map);
        return object.toString();
    }
}
