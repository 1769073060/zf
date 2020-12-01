package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.HpCustomerService;
import com.jiyeyihe.cre.webapp.entity.HpCustomerServiceVo;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.mapper.HpCustomerServiceMapper;
import com.jiyeyihe.cre.webapp.service.IHpCustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HpCustomerServiceImpl extends ServiceImpl<HpCustomerServiceMapper, HpCustomerService> implements IHpCustomerService {

    @Resource
    private HpCustomerServiceMapper hpCustomerServiceMapper;


    @Override
    public String getHpCustomerSelectList(Long pageNum, Long pageSize, String communityName,Long businessId) {
        JSONObject object = new JSONObject();
        IPage<HpCustomerServiceVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpCustomerServiceVo> queryWrapper = new QueryWrapper<HpCustomerServiceVo>();
        if (communityName!=null||"".equals(communityName)){
            queryWrapper.like("hi.community_name",communityName).or().like("hi.house_number",communityName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        List<HpCustomerServiceVo> selectList = hpCustomerServiceMapper.getHpCustomerSelectList(queryWrapper);
        page.setTotal(selectList.size());
        page.setPages(selectList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        selectList = hpCustomerServiceMapper.getHpCustomerSelectList(queryWrapper);
        page.setRecords(selectList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",selectList);
        return object.toString();
    }

    @Override
    public String getHpCustomerListPage(Long pageNum, Long pageSize, String houseName,Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpCustomerServiceVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (houseName!=null||"".equals(houseName)){
            queryWrapper.like("house_name",houseName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hcs.business_id",businessId);
        }
        List<HpCustomerServiceVo> roleList = hpCustomerServiceMapper.getHpCustomerListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hcs.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpCustomerServiceMapper.getHpCustomerListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public HpCustomerService getHpCustomerSelectById(Long id) throws Exception{
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hl.id",id);
        return hpCustomerServiceMapper.getHpCustomerSelectById(queryWrapper);
    }

    @Override
    public String getHpCustomersSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hcs.id",id);
        Map<String,Object> map  = hpCustomerServiceMapper.getHpCustomersSelectById(queryWrapper);
        object.put("dataList",map);
        return object.toString();
    }
}
