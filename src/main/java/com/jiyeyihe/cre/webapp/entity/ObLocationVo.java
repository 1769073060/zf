package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("t_ob_location")
public class ObLocationVo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer district;
    private String propertyName;
    private String address;
    private String officeDescrip;
    private Double longitude;
    private Double latitude;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

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
