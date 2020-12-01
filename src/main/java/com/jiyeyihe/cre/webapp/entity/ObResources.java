package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_resources")
public class ObResources {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long obInfoId;
    private Integer officeResourcesStatus;
    private String descrip;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;

    private String propertyName;
    private String houseNumber;
    private String districtName;
    private String provinceName;
}