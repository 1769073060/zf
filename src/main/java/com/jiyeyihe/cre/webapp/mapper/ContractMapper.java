package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.Contract;
import com.jiyeyihe.cre.webapp.entity.HpOrderVo;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ContractMapper extends BaseMapper<Contract> {

    /**
     * 查询最大值id
     */
    @Select("select id from t_contract where id = (select max(id) from t_contract)")
    Contract getContractSelectMaxId();


    @Select("select tc.id,tc.id, tc.contract_template_name contractTemplateName, tc.contract_template_content contractTemplateContent, tc.createtime, tc.updatetime, tc.business_id businessId\n" +
            "from t_contract tc\n" +
            "${ew.customSqlSegment}")
    List<Contract> getAppOrderSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("select tc.id,tc.id, tc.contract_template_name contractTemplateName, tc.contract_template_content contractTemplateContent, tc.createtime, tc.updatetime, tc.business_id businessId\n" +
            "from t_contract tc\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getIdContract(@Param(Constants.WRAPPER) Wrapper wrapper);
}
