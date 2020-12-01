package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_voice_reminder")
public class ObVoiceReminder{


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String vioceName;
    private String vioceAuditionUrl;
    private Integer vioceStatus;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

}
