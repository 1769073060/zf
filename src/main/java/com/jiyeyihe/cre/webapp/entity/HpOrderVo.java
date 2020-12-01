package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_hp_order")
public class HpOrderVo {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Long hpInfoId;
    private Integer paymentType;
    private String orderNo;
    private Integer orderStatus;
    private Double totalFee;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;
    private Integer clickStatus;

    private Double rental;
    private String carouselUrl;

    private String communityName;
    private Double houseArea;
    private Integer leaseTypeValue;
    private String leaseTypeName;
    private Integer houseTypeValue;
    private String houseTypeName;
    private Integer renovationTypeValue;
    private String renovationTypeName;
    private String districtName;
    private String cityName;
    private String provinceName;
    private Integer bedroom;
    private Integer drawingRoom;
    private Integer toilet;
    private Integer houseOrientationTypeValue;
    private String houseOrientationTypeName;
    private Long hpCustomerViewingRecordId;
}