package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpFeedbackQuestion;

public interface IHpFeedbackQuestionService extends IService<HpFeedbackQuestion> {

    /**
     * app 反馈类型列表查询
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    public String getAppHpFeedbackQuestionListPage(Long pageNum,Long pageSize,Long userId);
}
