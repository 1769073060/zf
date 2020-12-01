package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpResources;
import com.jiyeyihe.cre.webapp.entity.HpVoiceReminder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HpVoiceReminderMapper extends BaseMapper<HpVoiceReminder> {

    @Select("select hvr.vioce_audition_url vioceAuditionUrl,hvr.vioce_name viocename from t_hp_voice_reminder hvr"+
            " ${ew.customSqlSegment}")
    public List<HpVoiceReminder> getHpVoiceReminderListPage(@Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("select hvr.id,hvr.vioce_status  from t_hp_voice_reminder hvr ${ew.customSqlSegment}")
    HpVoiceReminder getHpVoiceReminderById(@Param(Constants.WRAPPER) Wrapper wrapper);
}
