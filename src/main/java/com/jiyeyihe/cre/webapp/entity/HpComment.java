package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_hp_comment")
public class HpComment {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpInfoId;
    private String commentContent;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

}
