package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.Dictionary;
import com.jiyeyihe.cre.webapp.entity.HpFeedbackQuestion;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.mapper.HpFeedbackQuestionMapper;
import com.jiyeyihe.cre.webapp.service.IHpFeedbackQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
public class HpFeedbackQuestionServiceImpl extends ServiceImpl<HpFeedbackQuestionMapper, HpFeedbackQuestion> implements IHpFeedbackQuestionService {

    @Resource
    private HpFeedbackQuestionMapper hpFeedbackQuestionMapper;

    @Override
    public String getAppHpFeedbackQuestionListPage(Long pageNum, Long pageSize, Long userId) {
        JSONObject object = new JSONObject();
        IPage<HpFeedbackQuestion> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("id");
        List<HpFeedbackQuestion> roleList = hpFeedbackQuestionMapper.selectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpFeedbackQuestionMapper.selectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }
}
