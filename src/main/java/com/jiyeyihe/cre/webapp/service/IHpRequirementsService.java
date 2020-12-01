package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpRequirements;


public interface IHpRequirementsService extends IService<HpRequirements> {

    /**
     * 更新房屋信息
     * @param hpInfo
     * @param hpRequirements
     * @return
     */
    public int updateHpInfoDetailRequirementsByLocationId(HpInfo hpInfo,HpRequirements hpRequirements);

    /**
     * 删除房屋信息
     * @param id
     * @return
     */
    public int delHpRequirementsById(Long id);
}
