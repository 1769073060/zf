package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.Notice;
import org.apache.ibatis.annotations.Param;

public interface INoticeService extends IService<Notice> {


    /**
     * 系统通知列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    String getAppNoticeSelectListPage(Long pageNum,Long pageSize);

    /**
     * 查询单个系统通知
     * @param id
     * @return
     * @throws Exception
     */
    public String getAppNoticeSelectById(@Param("id")Long id)throws Exception;

}
