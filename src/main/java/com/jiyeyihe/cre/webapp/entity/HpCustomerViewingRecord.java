package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_hp_customer_viewing_record")
public class HpCustomerViewingRecord {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpInfoId;
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



}