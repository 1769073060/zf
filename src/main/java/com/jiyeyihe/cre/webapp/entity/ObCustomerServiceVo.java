package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_customer_service")
public class ObCustomerServiceVo {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String officeName;
    private String consumerServiceName;
    private String consumerServicePhone;
    private String consumerServiceWechat;
    private String consumerServiceQq;
    private Long createtime;
    private Long businessId;
    private Long updatetime;
    private Long obInfoId;
    private Long userId;

    private String building;
    private String houseNumber;
    private String propertyName;
    private String districtName;
    private String cityName;
    private String provinceName;
}
