package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_notice")
public class Notice {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String descrip;
    private Integer messageStatus;
    private Long createtime;
    private Long updatetime;
}
