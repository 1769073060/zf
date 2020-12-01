package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_dictionary")
public class Dictionary implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("dic_key")
    private String dicKey;
    @TableField("dic_value")
    private Integer dicValue;
    @TableField("dic_name")
    private String dicName;
    @TableField("descrip")
    private String descrip;
    @TableField
    private String url;

}