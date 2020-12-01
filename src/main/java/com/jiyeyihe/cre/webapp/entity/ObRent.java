package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_rent")
public class ObRent {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long obInfoId;
    private Integer propertyFee;
    private Integer parkingFee;
    private Integer cableBill;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;
}