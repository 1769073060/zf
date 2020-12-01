package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_hp_rent")
public class HpRent {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpInfoId;
    private Integer managementFee;
    private Integer gasFee;
    private Integer heatingFee;
    private Integer cableBill;
    private Integer parkingFee;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;
}
