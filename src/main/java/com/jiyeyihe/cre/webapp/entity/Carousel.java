package com.jiyeyihe.cre.webapp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_carousel")
public class Carousel{

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String carouselUrl;
    private String carouselName;
    private Integer carouselStatus;
    private Long createtime;
    private Long updatetime;
}
