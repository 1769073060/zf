package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String mobile;
    private String email;
    private String nickname;
    private Integer gender;
    private String realname;
    private String idCard;
    private String portrait;
    private String regIp;
    private String lastLoginIp;
    private Long businessId;
    private Long createtime;
    private Long updatetime;
}
