package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_customer_service")
public class ObCustomerService {


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


}
