package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_district")
public class District {


    private Integer id;
    private String districtName;
    private String shortName;
    private Integer cityId;


}