package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("t_hp_furniture")
public class HpFurniture {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpInfoId;
    private Integer broadband;
    private Integer wardrobe;
    private Integer television;
    private Integer washingMachine;
    private Integer heating;
    private Integer heater;
    private Integer bed;
    private Integer sofa;
    private Integer airConditioner;
    private Integer refrigerator;
    private Integer balcony;
    private Integer privateToilet;
    private Integer mattress;
    private Integer bedsideCupboard;
    private Integer smartLock;
    private Integer teaTable;
    private Integer diningTable;
    private Integer computerDesk;
    private Integer gasStoves;
    private Integer rangeHood;
    private Long businessId;
    private Long userId;
    private Long createtime;
    private Long updatetime;

}
