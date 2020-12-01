package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpComment;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpResources;


public interface IHpResourcesService extends IService<HpResources> {

    /**
     * 发布房源分页查询
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @param businessId
     * @return
     */
    public String getHpResourceSelectListPage(Long pageNum,Long pageSize,String communityName,Long businessId);

    /**
     * 获取单个房屋信息
     * @param hpInfoId
     * @return
     */
    public HpResources getHpResourceById(Long hpInfoId);

    /**
     * 获取新增发布房源里面的弹出框
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @param businessId
     * @return
     */
    public String getHpResourcesSelectList(Long pageNum, Long pageSize, String communityName,Long businessId);

    /**
     * 删除发布的房源
     * @param id
     * @return
     */
    public int delHpResourcesById(Long id);


    /**
     * 发布app房源评价
     * @param hpComment
     * @param id
     * @return
     */
    public int addAppHpInfoComment(HpComment hpComment, Long id);

    /**
     * app我发布的房源信息列表查询
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    public String getAppHpResourceSelectListPage(Long pageNum,Long pageSize,Long userId);

}
