package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("t_hp_location")
public class HpLocationVo {
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
    private Long createtime;
    private Long updatetime;

    private Long provinceId;
    private String provinceName;
    private String provinceShortName;
    private Long cityId;
    private String cityName;
    private String cityShortName;
    private Long districtId;
    private String districtName;
    private String districtShortName;
}
