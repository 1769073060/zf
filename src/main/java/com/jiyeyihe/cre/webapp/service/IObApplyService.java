package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpApply;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import org.apache.ibatis.annotations.Param;


public interface IObApplyService extends IService<ObApply> {


    /**
     * 平台端写字楼 申请信息待审核分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getObApplyPendingListPage(Long pageNum,Long pageSize,String merchantType);


    /**
     * 平台端写字楼 申请信息已通过分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getObApplyPassedListPage(Long pageNum,Long pageSize,String merchantType);


    /**
     * 添加写字楼申请信息企业认证
     * @param apply
     * @return
     */
    int addAppObApplyEnterpriseCertification(HpApply apply);

    /**
     * 平台端 写字楼 申请信息未通过分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getObApplyNotPassListPage(Long pageNum,Long pageSize,String merchantType);


    /**
     * 平台端 写字楼 查询单个我的申请
     * @param id
     * @return
     */
    public String getByIdObApply(@Param("id")Long id)throws Exception;

    /**
     * 平台端 写字楼 修改申请信息状态
     * @param id
     * @return
     */
    public ObApply getObApplyById(Long id);

    /**
     * 平台端 修改id到用户表的businessId
     * @param byId
     * @return
     */
    public int updateObApplyById(ObApply byId);



    /**
     * 商家端 写字楼查询单个id
     * @param id
     * @return
     */
    public String getByIdObApplySelect(@Param("id")Long id)throws Exception;

    /**
     * 商家端 写字楼我的申请信息分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getObApplyListPage(Long pageNum,Long pageSize,String merchantType,Long businessId);

    /**
     * 平台端  写字楼查询已经审核通过的用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    public String getObApplySelectListPage(Long pageNum,Long pageSize);
}
