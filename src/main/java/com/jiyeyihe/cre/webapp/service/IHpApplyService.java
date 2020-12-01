package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;


public interface IHpApplyService extends IService<HpApply> {
    /**
     * app租房 查询单个我的申请
     * @param id
     * @return
     */
    public String getAppHpApplySelectById(@Param("id")Long id)throws Exception;


    /**
     * app  我的申请信息分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getAppApplyListPage(Long pageNum,Long pageSize,String merchantType,Long userId) throws Exception;


    /**
     *  添加租房申请信息企业认证
     * @param hpApply
     * @return
     */
    int addAppHpApplyEnterpriseCertification(HpApply hpApply);


    /**
     * 平台端租房 申请信息待审核分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getHpApplyPendingListPage(Long pageNum,Long pageSize,String merchantType);


    /**
     * 平台端租房 申请信息已通过分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getHpApplyPassedListPage(Long pageNum,Long pageSize,String merchantType);

    /**
     * 平台端租房 申请信息未通过分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getHpApplyNotPassListPage(Long pageNum,Long pageSize,String merchantType);


    /**
     * 平台端租房 查询单个我的申请
     * @param id
     * @return
     */
    public String getByIdHpApply(@Param("id")Long id)throws Exception;

    /**
     * 平台端租房 修改申请信息状态
     * @param id
     * @return
     */
    public HpApply getHpApplyById(Long id);


    /**
     * 平台端 修改id到用户表的businessId
     * @param byId
     * @return
     */
    public int updateHpApplyById(HpApply byId);




    /**
     * 商家端 查询单个我的申请
     * @param id
     * @return
     */
    public String getByIdHpApplySelect(@Param("id")Long id)throws Exception;

    /**
     * 商家端 租房我的申请信息分页查询
     * @param pageNum
     * @param pageSize
     * @param merchantType
     * @return
     */
    public String getHpApplyListPage(Long pageNum,Long pageSize,String merchantType,Long businessId);

    /**
     * 平台端  租房查询已经审核通过的用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    public String getHpApplySelectListPage(Long pageNum,Long pageSize);

}
