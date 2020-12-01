package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
@TableName("t_hp_location")
public class HpLocation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer district;
    private String communityName;
    private String building;
    private String address;
    private String descrip;
    private Integer elevator;
    private Integer parkingSpace;
    private Double longitude;
    private Double latitude;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;


}
