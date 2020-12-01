package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.mapper.NoticeMapper;
import com.jiyeyihe.cre.webapp.service.INoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public String getAppNoticeSelectListPage(Long pageNum, Long pageSize) {
        JSONObject object = new JSONObject();
        IPage<Contract> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Contract> roleList = noticeMapper.selectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = noticeMapper.selectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();    }

    @Override
    public String getAppNoticeSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        UpdateWrapper updateWrapper = new UpdateWrapper();

        queryWrapper.eq("id",id);
        Notice notice = noticeMapper.selectOne(queryWrapper);
        if (notice.getMessageStatus()== MsgConsts.ZERO_STATUS){
            notice.setMessageStatus(MsgConsts.FIRST_STATUS);
        }
        updateWrapper.eq("id",notice.getId());
        noticeMapper.update(notice,updateWrapper);
        object.put("dataList",notice);
        return object.toString();
    }

}
