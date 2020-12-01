package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_city")
public class City {

    private Long id;
    private String cityName;
    private String shortName;
    private Integer provinceId;


}