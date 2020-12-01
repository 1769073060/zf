package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpVoiceReminder;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface IHpVoiceReminderService extends IService<HpVoiceReminder> {


    /**
     * 查询单个客户语音提醒
     * @param id
     * @return
     * @throws Exception
     */
    public String getHpVoiceReminderSelectById(Long id) throws Exception;

    /**
     * 客户语音提醒分页查询
     * @param pageNum
     * @param pageSize
     * @param vioceName
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getHpVoiceReminderListPage(Long pageNum,Long pageSize,String vioceName,Long businessId) throws Exception;

    /**
     * 修改客户语音状态
     * @param id
     * @return
     */
    public HpVoiceReminder getHpVoiceReminderById(Long id);

}
