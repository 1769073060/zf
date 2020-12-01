package com.jiyeyihe.cre.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpCustomerServiceVo;
import com.jiyeyihe.cre.webapp.entity.ObCustomerService;
import com.jiyeyihe.cre.webapp.entity.ObCustomerServiceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ObCustomerServiceMapper extends BaseMapper<ObCustomerService> {

    /**
     * 查询写字楼房产客服房源名称下拉框
     * @return
     */
    @Select("select hi.id obInfoId,hi.property_name propertyName,hi.house_number houseNumber,hi.building,\n" +
            "sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            " from t_ob_location hl\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "RIGHT JOIN t_ob_info hi on hl.id = hi.ob_location_id" +
            " ${ew.customSqlSegment}")
    List<ObCustomerServiceVo> getObCustomerSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 房产客服分页查询
     * @param wrapper
     * @return
     */
    @Select("select hcs.*,hi.property_name propertyName,hi.house_number houseNumber,\n" +
            "sd.district_name,sc.city_name,sp.province_name\n" +
            "from t_ob_customer_service hcs\n" +
            "LEFT JOIN t_ob_info hi on hcs.ob_info_id = hi.id\n" +
            "LEFT JOIN t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "LEFT JOIN sys_district sd on hl.district = sd.id\n" +
            "LEFT JOIN sys_city sc on sd.city_id = sc.id\n" +
            "LEFT JOIN sys_province sp on sc.province_id = sp.id" +
            " ${ew.customSqlSegment}")
    List<ObCustomerServiceVo> getObCustomerListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据id查询，展示写字楼房产客服信息
     * @param wrapper
     * @return
     */
    @Select("select hcs.ob_info_id obInfoId,hcs.consumer_service_wechat consumerServiceWechat,\n" +
            "hcs.consumer_service_qq consumerServiceQq,hcs.office_name officeName,hcs.consumer_service_phone consumerServicePhone,\n" +
            "hi.property_name propertyName,hi.house_number houseNumber,\n" +
            "sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_customer_service hcs\n" +
            "LEFT JOIN t_ob_info hi on hcs.ob_info_id = hi.id\n" +
            "LEFT JOIN t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "LEFT JOIN sys_district sd on hl.district = sd.id\n" +
            "LEFT JOIN sys_city sc on sd.city_id = sc.id\n" +
            "LEFT JOIN sys_province sp on sc.province_id = sp.id" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getObCustomersSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);
}
