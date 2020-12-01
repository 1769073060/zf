package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.ObVoiceReminder;

public interface IObVoiceReminderService extends IService<ObVoiceReminder> {

    /**
     * 查询写字楼单个客户语音提醒
     * @param id
     * @return
     * @throws Exception
     */
    public String getObVoiceReminderSelectById(Long id) throws Exception;

    /**
     * 写字楼客户语音提醒分页查询
     * @param pageNum
     * @param pageSize
     * @param vioceName
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getObVoiceReminderListPage(Long pageNum,Long pageSize,String vioceName,Long businessId) throws Exception;

    /**
     * 查询写字楼客户语音id
     * @param id
     * @return
     */
    public ObVoiceReminder getObVoiceReminderById(Long id);

}
