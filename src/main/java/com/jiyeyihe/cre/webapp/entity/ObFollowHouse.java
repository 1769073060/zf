package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_ob_follow_house")
public class ObFollowHouse{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户id
    @TableField("user_id")
    private Long userId;
    //写字楼id
    @TableField("ob_info_id")
    private Long obInfoId;
    //关注状态: 0未关注，1已关注
    @TableField("attention_status")
    private Integer attentionStatus;
}
