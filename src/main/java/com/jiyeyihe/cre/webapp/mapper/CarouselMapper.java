package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.Carousel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface CarouselMapper extends BaseMapper<Carousel> {


    @Select("select tc.carousel_status carouselStatus,tc.createtime,tc.carousel_url carouselUrl,tc.id,tc.updatetime,tc.carousel_name carouselName\n" +
            "from t_carousel tc\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getCarouselSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);
}
