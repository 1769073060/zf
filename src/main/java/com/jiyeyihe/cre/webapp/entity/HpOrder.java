package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_hp_order")
public class HpOrder {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Long hpInfoId;
    private Integer paymentType;
    private String orderNo;
    private Integer orderStatus;
    private Double totalFee;
    private String frontIdCardUrl;
    private String backIdCardUrl;
    private String contractUrl;
    private String idCard;
    private String contactName;
    private String phoneNumber;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;
    private Integer clickStatus;
}