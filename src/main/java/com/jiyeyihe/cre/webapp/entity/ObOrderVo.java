package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_order")
public class ObOrderVo {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Long obInfoId;
    private Integer paymentType;
    private String orderNo;
    private Integer orderStatus;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;
    private Double totalFee;
    private Integer clickStatus;

    private Double rentalDay;
    private Double rentalMonth;
    private String carouselUrl;
    private String propertyName;

    private Double constructionArea;
    private Integer officeBuildingType;
    private Integer houseOrientationType;
    private Long obHouseResourcesId;
    private Integer officeResourcesStatus;
    private String districtName;
    private String cityName;
    private String provinceName;
    private Integer houseOrientationTypeValue;
    private String  houseOrientationTypeName;
    private Integer leaseTypeValue;
    private String leaseTypeName;
    private Integer officeBuildingTypeValue;
    private String  officeBuildingTypeName;
    private Integer natureTypeValue;
    private String  natureTypeName;
    private Integer floorHeightTypeValue;
    private String  floorHeightTypeName;
    private Integer registeredTypeValue;
    private String  registeredTypeName;
    private Integer segmentationTypeValue;
    private String  segmentationTypeName;
    private Long obCustomerViewingRecordId;
}
