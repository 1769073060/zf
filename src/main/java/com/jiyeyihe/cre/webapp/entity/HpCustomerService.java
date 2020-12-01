package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_hp_customer_service")
public class HpCustomerService {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("房屋名称")
    private String houseName;
    private String consumerServiceName;
    @ApiModelProperty("客服电话")
    private String consumerServicePhone;
    @ApiModelProperty("客服微信")
    private String consumerServiceWechat;
    @ApiModelProperty("客服QQ")
    private String consumerServiceQq;
    @ApiModelProperty("房屋详情Id")
    private Long hpInfoId;
    private String houseNumber;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;


}
