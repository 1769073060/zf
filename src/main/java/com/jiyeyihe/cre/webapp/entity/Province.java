package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_province")
public class Province {

    private Long id;
    private String provinceName;
    private String shortName;

}