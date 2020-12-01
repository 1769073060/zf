package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpCustomerService;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.entity.HpResources;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface HpResourcesMapper extends BaseMapper<HpResources> {



    /**
     * 查询发布的房源
     * @param wrapper
     * @return
     */
    @Select("select hr.hp_info_id hpInfoId,hr.house_resources_status houseResourcesStatus,hr.descrip," +
            "hi.community_name communityName,hi.house_number houseNumber,hi.lease_type leaseType,hi.rental,\n" +
            "hi.bedroom,hi.drawing_room,hi.toilet,hi.id\n" +
            "from t_hp_resources hr\n" +
            "LEFT JOIN t_hp_info hi on hr.hp_info_id = hi.id" +
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getHpResourceSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Update("update t_hp_resources set house_resources_status=#{house_resources_status} where hp_info_id=#{HpInfoId}")
    int updateHpResourcePostStatus(@Param("HpInfoId") Long HpInfoId,@Param("house_resources_status") Integer houseResourcesStatus);


    /**
     * 初始化 新增发布房源里面的弹出框
     * @return
     */
    @Select("select hi.id,hi.community_name communityName,hi.house_number houseNumber,\n" +
            "sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_location hl \n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "RIGHT JOIN t_hp_info hi on hl.id = hi.hp_location_id" +
            " ${ew.customSqlSegment}")
    List<HpResources> getHpResourcesSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);



    @Select("select hs.id,hs.hp_info_id hpHouseInfoId,hs.house_resources_status houseResourcesStatus from t_hp_resources hs ${ew.customSqlSegment}")
    HpResources getHpResourceById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 小程序查询我发布的房源
     * @param wrapper
     * @return
     */
    @Select("select hr.hp_info_id hpInfoId,hr.house_resources_status houseResourcesStatus,\n" +
            "hi.id,hi.house_number houseNumber,hi.lease_type leaseType,hi.rental,hi.house_area houseArea,\n" +
            "hi.bedroom,hi.drawing_room,hi.toilet,\n" +
            "hl.community_name communityName,\n" +
            "sd.dic_value leaseTypeValue,sd.dic_name leaseTypeName,\n" +
            "sd1.dic_value houseOrientationTypeValue,sd1.dic_name houseOrientationTypeName,\n" +
            "sd2.dic_value bedroomTypeValue,sd2.dic_name bedroomTypeName,\n" +
            "sd3.dic_value bedroomFacingTypeValue,sd3.dic_name bedroomFacingTypeName\n" +
            "from t_hp_resources hr\n" +
            "LEFT JOIN t_hp_info hi on hr.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_dictionary sd on hi.lease_type = sd.dic_value\n" +
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.bedroom_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.bedroom_facing_type = sd3.dic_value" +
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getAppHpResourceSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);

}
