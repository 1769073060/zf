package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@TableName("t_hp_follow_house")
public class HpFollowHouse {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户id
    @TableField("user_id")
    private Long userId;
    //房产信息id
    @TableField("hp_info_id")
    private Long hpInfoId;
    //关注状态: 0未关注，1已关注
    @TableField("attention_status")
    private Integer attentionStatus;
}
