package com.jiyeyihe.cre.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpFeedbackQuestion;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface HpFollowHouseMapper extends BaseMapper<HpFollowHouse> {


    /**
     * 查询单个关注状态
     * @param wrapper
     * @return
     */
    @Select("select hfh.attention_status attentionStatus from t_hp_follow_house hfh ${ew.customSqlSegment}")
    HpFollowHouse getAppHpResourceFollow(@Param(Constants.WRAPPER) Wrapper wrapper);
}
