package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_hp_feedback_question")
public class HpFeedbackQuestion {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String feedbackType;
    private String feedbackContent;
    private Long userId;
    private Long createtime;
    private Long updatetime;
}
