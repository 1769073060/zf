package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_info")
public class ObInfoVo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long obLocationId;
    private String propertyName;
    private String building;
    private String houseNumber;
    private Integer leaseType;
    private Integer officeBuildingType;
    private Integer officeLevel;
    private Integer nature;
    private String carouselUrl;
    private String vrPicturesUrl;
    private String videoUrl;
    private String officeDescription;
    private Integer floor;
    private Integer totalFloor;
    private Integer floorHeight;
    private Double constructionArea;
    private Double usageRate;
    private Integer renovationCondition;
    private Integer houseOrientationType;
    private Integer payWayType;
    private Integer leaseTermType;
    private Double rentalDay;
    private Double rentalMonth;
    private Integer rentFreePeriod;
    private Long startStation;
    private Long endStation;
    private Integer registered;
    private Integer segmentation;
    private Double agencyFee;
    private Double serviceFee;
    private Integer quantityStatus;
	private Double waterFree;
    private Double electricityBill;
    private Double networkFee;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

    private Long obInfoId;
    private Integer officeResourcesStatus;
    private Integer viewingStatus;
    private String descrip;
    private Long obResourcesId;
    private String districtName;
    private String cityName;
    private String provinceName;
    private String officeBuildingTypeName;
    private String houseOrientationTypeName;
    private String natureTypeName;
    private Long obCustomerViewingRecordId;
    private Double longitude;
    private Double latitude;
    private Double distance;
    private Integer paymentStatus;

    private Long districtId;
    private Long cityId;
    private Long provinceId;
    private Integer payWayTypeValue;
    private String payWayTypeName;
    private Integer obAppointmentHouseId;

}
