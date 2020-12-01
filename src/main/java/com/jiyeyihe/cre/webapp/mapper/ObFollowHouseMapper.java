package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;
import com.jiyeyihe.cre.webapp.entity.ObFollowHouse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ObFollowHouseMapper extends BaseMapper<ObFollowHouse> {


    /**
     * 查询单个写字楼关注状态
     * @param wrapper
     * @return
     */
    @Select("select hfh.attention_status attentionStatus from t_ob_follow_house hfh ${ew.customSqlSegment}")
    ObFollowHouse getAppObResourceFollow(@Param(Constants.WRAPPER) Wrapper wrapper);
}
