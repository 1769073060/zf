package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_ob_appointment_house")
public class ObAppointmentHouse {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户id
    @TableField("user_id")
    private Long userId;
    //房产信息id
    @TableField("ob_info_id")
    private Long obInfoId;
    //约房状态，0、未约房，1、已约房
    @TableField("appointment_status")
    private Integer appointmentStatus;
    //创建时间
    private Long createtime;
    //修改时间
    private Long updatetime;

}
