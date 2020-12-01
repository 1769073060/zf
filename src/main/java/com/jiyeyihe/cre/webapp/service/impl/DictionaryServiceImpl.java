package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.Dictionary;
import com.jiyeyihe.cre.webapp.mapper.DictionaryMapper;
import com.jiyeyihe.cre.webapp.service.IDictionaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
@Transactional
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public String getDictionaryList(String key) throws Exception {
        JSONObject jsonObject = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dic_key",key);
        List<Dictionary> list = dictionaryMapper.selectList(queryWrapper);
        jsonObject.put("listData",list);
        return jsonObject.toString();


    }

    @Override
    public String getDictionaryListPage(Long pageNum,Long pageSize,String dicKey) throws Exception {
        JSONObject object = new JSONObject();
        IPage<Dictionary> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dic_key",dicKey);
        List<Dictionary> roleList = dictionaryMapper.selectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = dictionaryMapper.selectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }
}