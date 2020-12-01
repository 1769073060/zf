package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_customer_viewing_record")
public class ObCustomerViewingRecordVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long obInfoId;
    private String weekTime;
    private String hourTime;
    private Long viewHouseTime;
    private String customerName;
    private String contactNumber;
    private Integer viewingStatus;
    private Long livingPopulation;
    private Integer paymentStatus;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

    private Long obCustomerViewingRecordId;
    private Double rentalDay;
    private Double rentalMonth;
    private String propertyName;
    private String districtName;
    private String cityName;
    private String houseNumber;
    private String provinceName;



}