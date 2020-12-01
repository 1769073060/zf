package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpVoiceReminder;
import com.jiyeyihe.cre.webapp.entity.ObVoiceReminder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ObVoiceReminderMapper extends BaseMapper<ObVoiceReminder> {


    @Select("select hvr.id,hvr.vioce_status  from t_ob_voice_reminder hvr ${ew.customSqlSegment}")
    ObVoiceReminder getObVoiceReminderById(@Param(Constants.WRAPPER) Wrapper wrapper);
}
