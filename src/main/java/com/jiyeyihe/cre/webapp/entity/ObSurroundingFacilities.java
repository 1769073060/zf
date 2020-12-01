package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 写字楼周边配套设施
 */
@Data
@TableName("t_ob_surrounding_facilities")
public class ObSurroundingFacilities {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long obInfoId;
    private Integer superStore;
    private Integer school;
    private Integer expressWay;
    private Integer hospital;
    private Integer shoppingMall;
    private Integer park;
    private Integer bulletTrain;
    private Integer bank;
    private Integer businessCircle;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;

}
