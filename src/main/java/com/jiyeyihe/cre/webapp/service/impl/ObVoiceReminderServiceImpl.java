package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.webapp.entity.ObVoiceReminder;
import com.jiyeyihe.cre.webapp.mapper.ObVoiceReminderMapper;
import com.jiyeyihe.cre.webapp.service.IObVoiceReminderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
@Transactional
public class ObVoiceReminderServiceImpl extends ServiceImpl<ObVoiceReminderMapper, ObVoiceReminder> implements IObVoiceReminderService {

    @Resource
    private ObVoiceReminderMapper obVoiceReminderMapper;
    @Resource
    private FileUtils fileUtils;

    @Override
    public String getObVoiceReminderSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        ObVoiceReminder ObVoiceReminder = obVoiceReminderMapper.selectById(id);
        ObVoiceReminder.setVioceAuditionUrl(fileUtils.getIpaURl(ObVoiceReminder.getVioceAuditionUrl()));
        ObVoiceReminder selectById = obVoiceReminderMapper.selectById(id);
        selectById.setVioceAuditionUrl(ObVoiceReminder.getVioceAuditionUrl());
        object.put("dataList",selectById);
        return object.toString();
    }

    /**
     * 分页查询客戶语音
     * @param pageNum
     * @param pageSize
     * @param vioceName
     * @return
     * @throws Exception
     */
    @Override
    public String getObVoiceReminderListPage(Long pageNum,Long pageSize,String vioceName,Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        Map map = new HashMap();
        IPage<ObVoiceReminder> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (vioceName!=null||"".equals(vioceName)){
            queryWrapper.like("vioce_name",vioceName);
        }
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("business_id",businessId);
        }
        List<ObVoiceReminder> listPage = obVoiceReminderMapper.selectList(queryWrapper);
        page.setTotal(listPage.size());
        page.setPages(listPage.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);

        listPage = obVoiceReminderMapper.selectList(queryWrapper);
        List<ObVoiceReminder> arrayList = new ArrayList<>();
        for(ObVoiceReminder ObVoiceReminder:listPage){
            if(!isEmpty(ObVoiceReminder.getVioceAuditionUrl())){
                ObVoiceReminder.setVioceAuditionUrl(fileUtils.getIpaURl(ObVoiceReminder.getVioceAuditionUrl()));
            }
            arrayList.add(ObVoiceReminder);
        }
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",arrayList);
        return object.toString();
    }

    @Override
    public ObVoiceReminder getObVoiceReminderById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hvr.id",id);
        return obVoiceReminderMapper.getObVoiceReminderById(queryWrapper);
    }
}
