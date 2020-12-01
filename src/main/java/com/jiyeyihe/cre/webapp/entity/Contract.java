package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_contract")
public class Contract  {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String contractTemplateName;
    private String contractTemplateContent;
    private String contract;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
}
