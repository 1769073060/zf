package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.Contract;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.mapper.ContractMapper;
import com.jiyeyihe.cre.webapp.service.IContractService;
import com.jiyeyihe.cre.webapp.service.IContractService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements IContractService {

    @Resource
    private ContractMapper contractMapper;

    @Override
    public String getIdContract(Long id) {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tc.id",id);
        Map<String, Object> selectById = contractMapper.getIdContract(queryWrapper);
        object.put("dataList",selectById);
        return object.toString();
    }

    @Override
    public String getContractListPage(Long pageNum, Long pageSize, String contractTemplateName, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<Contract> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (contractTemplateName!=null||"".equals(contractTemplateName)){
            queryWrapper.like("tc.contract_template_name",contractTemplateName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("tc.business_id",businessId);
        }
        List<Contract> roleList = contractMapper.getAppOrderSelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("tc.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = contractMapper.getAppOrderSelectListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getAppContractSelectListPage(Long pageNum, Long pageSize, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<Contract> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("business_id",businessId);
        }
        List<Contract> roleList = contractMapper.selectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = contractMapper.selectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public Contract listMaxId() {
        Contract selectMaxId = contractMapper.getContractSelectMaxId();
        return selectMaxId;
    }
}
