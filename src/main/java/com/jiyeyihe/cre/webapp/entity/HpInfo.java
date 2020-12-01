package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@TableName("t_hp_info")
public class HpInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpLocationId;
    private String communityName;
    private String houseNumber;
    private Integer leaseType;
    private Integer viewingTime;
    private Integer checkinTime;
    private String carouselUrl;
    private String vrPicturesUrl;
    private String videoUrl;
    private String houseDescription;
    private Integer bedroom;
    private Integer drawingRoom;
    private Integer toilet;
    private Integer houseType;
    private Integer floor;
    private Integer totalFloor;
    private Integer floorHeight;
    private Double houseArea;
    private Integer renovationCondition;
    private Integer houseOrientationType;
    private Integer payWayType;
    private Integer leaseTermType;
    private Double rental;
    private Integer bedroomType;
    private Integer bedroomFacingType;
    private Double squareMeter;
    private Integer attentionStatus;
    private Double agencyFee;
    private Double serviceFee;
    private Double waterFree;
    private Double electricityBill;
    private Double networkFee;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;


}
