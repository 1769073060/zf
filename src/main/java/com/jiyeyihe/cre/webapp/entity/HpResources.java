package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_hp_resources")
public class HpResources {


    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;
    private Long hpInfoId;
    private Integer houseResourcesStatus;
    @ApiModelProperty("描述")
    private String descrip;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;

    private String communityName;
    private String houseNumber;
    private String districtName;
    private String cityName;
    private String provinceName;
}
