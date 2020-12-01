package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_info")
public class ObInfo{
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
	private Double waterFree;
    private Double electricityBill;
    private Double networkFee;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;
}
