package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_apply")
public class ObApplyVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String applyName;
    private Integer age;
    private String idCard;
    private String frontIdCardUrl;
    private String backIdCardUrl;
    private String businessLicensePictureUrl;
    private String currentResidenceProvince;
    private String currentResidenceCity;
    private String currentResidenceDistrict;
    private String currentResidentialAddress;
    private String phoneNumber;
    private Integer applyStatus;
    private Integer merchantType;
    private Integer applyRentalType;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

    private String districtName;
    private String cityName;
    private String provinceName;
}
