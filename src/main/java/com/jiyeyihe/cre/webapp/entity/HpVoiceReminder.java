package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_hp_voice_reminder")
public class HpVoiceReminder {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String vioceName;
    private Integer vioceStatus;
    private String vioceAuditionUrl;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;
}