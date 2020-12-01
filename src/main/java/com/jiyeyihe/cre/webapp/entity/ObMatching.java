package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_matching")
public class ObMatching  {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long obInfoId;
    private Integer elevator;
    private Integer water;
    private Integer electric;
    private Integer airConditioner;
    private Integer officeFurniture;
    private Integer centralHeating;
    private Integer staffCanteen;
    private Integer securityMonitoring;
    private Integer freeParking;
    private Integer cableTv;
    private Integer broadband;
    private Long createtime;
    private Long updatetime;
    private Long businessId;
    private Long userId;
}
