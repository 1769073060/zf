package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.webapp.entity.HpVoiceReminder;
import com.jiyeyihe.cre.webapp.mapper.HpVoiceReminderMapper;
import com.jiyeyihe.cre.webapp.service.IHpVoiceReminderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
@Transactional
public class HpVoiceReminderServiceImpl extends ServiceImpl<HpVoiceReminderMapper, HpVoiceReminder> implements IHpVoiceReminderService {
    @Resource
    private HpVoiceReminderMapper hpVoiceReminderMapper;
    @Resource
    private FileUtils fileUtils;

    /**
     * 查詢 单个客戶语音
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String getHpVoiceReminderSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        HpVoiceReminder houseVoiceReminder = hpVoiceReminderMapper.selectById(id);
        houseVoiceReminder.setVioceAuditionUrl(fileUtils.getIpaURl(houseVoiceReminder.getVioceAuditionUrl()));
        HpVoiceReminder selectById = hpVoiceReminderMapper.selectById(id);
        selectById.setVioceAuditionUrl(houseVoiceReminder.getVioceAuditionUrl());
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
    public String getHpVoiceReminderListPage(Long pageNum,Long pageSize,String vioceName,Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        Map map = new HashMap();
        IPage<HpVoiceReminder> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (vioceName!=null||"".equals(vioceName)){
            queryWrapper.like("vioce_name",vioceName);
        }
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("business_id",businessId);
        }
        List<HpVoiceReminder> listPage = hpVoiceReminderMapper.selectList(queryWrapper);
        page.setTotal(listPage.size());
        page.setPages(listPage.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);

        listPage = hpVoiceReminderMapper.selectList(queryWrapper);
        List<HpVoiceReminder> arrayList = new ArrayList<>();
        for(HpVoiceReminder HpVoiceReminder:listPage){
            if(!isEmpty(HpVoiceReminder.getVioceAuditionUrl())){
                HpVoiceReminder.setVioceAuditionUrl(fileUtils.getIpaURl(HpVoiceReminder.getVioceAuditionUrl()));
            }
            arrayList.add(HpVoiceReminder);
        }
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",arrayList);
        return object.toString();
    }

    @Override
    public HpVoiceReminder getHpVoiceReminderById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hvr.id",id);
        return hpVoiceReminderMapper.getHpVoiceReminderById(queryWrapper);
    }
}
