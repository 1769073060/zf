package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpCustomerService;
import com.jiyeyihe.cre.webapp.entity.HpCustomerServiceVo;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HpCustomerServiceMapper extends BaseMapper<HpCustomerService> {

    /**
     * 查询房产客服房源名称下拉框
     * @return
     */
    @Select("select hi.id hpInfoId,hi.community_name communityName,hi.house_number houseNumber,\n" +
            "sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            " from t_hp_location hl \n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "RIGHT JOIN t_hp_info hi on hl.id = hi.hp_location_id" +
            " ${ew.customSqlSegment}")
    List<HpCustomerServiceVo> getHpCustomerSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 房产客服分页查询
     * @param wrapper
     * @return
     */
    @Select("select hcs.*,hi.community_name communityName,hi.house_number houseNumber,\n" +
            "sd.district_name,sc.city_name,sp.province_name\n" +
            "from t_hp_customer_service hcs\n" +
            "LEFT JOIN t_hp_info hi on hcs.hp_info_id = hi.id\n" +
            "LEFT JOIN t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "LEFT JOIN sys_district sd on hl.district = sd.id\n" +
            "LEFT JOIN sys_city sc on sd.city_id = sc.id\n" +
            "LEFT JOIN sys_province sp on sc.province_id = sp.id" +
            " ${ew.customSqlSegment}")
    List<HpCustomerServiceVo> getHpCustomerListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id查询，修改房产客服信息
     * @param wrapper
     * @return
     */
    @Select("select *"+
            "from t_hp_customer_service hl \n" +
            " ${ew.customSqlSegment}")
    HpCustomerService getHpCustomerSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 根据id查询，展示房产客服信息
     * @param wrapper
     * @return
     */
    @Select("select hcs.id,hcs.hp_info_id hpInfoId,hcs.consumer_service_wechat consumerServiceWechat," +
            "hcs.consumer_service_qq consumerServiceQq,hcs.house_name houseName,hcs.consumer_service_phone consumerServicePhone," +
            "hi.community_name communityName,hi.house_number houseNumber,\n" +
            "sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_customer_service hcs\n" +
            "LEFT JOIN t_hp_info hi on hcs.hp_info_id = hi.id\n" +
            "LEFT JOIN t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "LEFT JOIN sys_district sd on hl.district = sd.id\n" +
            "LEFT JOIN sys_city sc on sd.city_id = sc.id\n" +
            "LEFT JOIN sys_province sp on sc.province_id = sp.id" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getHpCustomersSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);
}
