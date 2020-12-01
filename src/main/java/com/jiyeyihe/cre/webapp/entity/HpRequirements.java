package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("t_hp_requirements")
public class HpRequirements {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpInfoId;
    private Integer girlsOnly;
    private Integer noPets;
    private Integer normalSchedule;
    private Integer noSmoking;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;
}
