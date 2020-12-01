package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.ObInfoVo;
import com.jiyeyihe.cre.webapp.entity.ObResources;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ObResourcesMapper extends BaseMapper<ObResources> {
    /**
     * 查询发布的写字楼
     * @param wrapper
     * @return
     */
    @Select("select hr.ob_info_id obInfoId,hr.office_resources_status officeResourcesStatus,hr.descrip,hi.nature,\n" +
            "hi.id,hi.property_name propertyName,hi.building,hi.house_number houseNumber,hi.lease_type leaseType,hi.rental_day rentalDay,hi.rental_month rentalMonth\n" +
            "from t_ob_resources hr\n" +
            "LEFT JOIN t_ob_info hi on hr.ob_info_id = hi.id" +
            " ${ew.customSqlSegment}")
    List<ObInfoVo> getObResourceSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);



    /**
     * 初始化 新增发布房源里面的弹出框
     * @return
     */
    @Select("select hi.id,hi.property_name propertyName,hi.building,hi.house_number houseNumber,\n" +
            "sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_location hl\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "RIGHT JOIN t_ob_info hi on hl.id = hi.ob_location_id" +
            " ${ew.customSqlSegment}")
    List<ObResources> getObResourcesSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("select hs.id,hs.ob_info_id obInfoId,hs.office_resources_status officeResourcesStatus from t_ob_resources hs ${ew.customSqlSegment}")
    ObResources getObResourceById(@Param(Constants.WRAPPER) Wrapper wrapper);
}
