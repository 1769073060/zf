package com.jiyeyihe.cre.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.entity.HpLocationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface HpLocationMapper extends BaseMapper<HpLocation> {


    /**
     * 房产区域分页查询
     * @param wrapper
     * @return
     */
    @Select("select hl.*,sd.district_name,sd.short_name districtShortName,sd.id districtId,\n" +
            "sc.city_name cityName,sc.short_name cityShortName,sc.id cityId,\n" +
            "sp.province_name provinceName,sp.short_name provinceShortName,sp.id provinceId\n" +
            "from t_hp_location hl \n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<HpLocationVo> getHpLocationSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 查询单个房产区域
     * @param wrapper
     * @return
     */
    @Select("select hl.id,hl.district,hl.community_name communityName,hl.building,hl.address,hl.descrip," +
            "hl.elevator,hl.parking_space parkingSpace,hl.updatetime,hl.createtime,hl.longitude,hl.latitude,\n" +
            "sd.district_name districtName,sd.short_name districtShortName,sd.id districtId,\n" +
            "sc.city_name cityName,sc.short_name cityShortName,sc.id cityId,\n" +
            "sp.province_name provinceName,sp.short_name provinceShortName,sp.id provinceId\n" +
            "from t_hp_location hl \n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getHpLocationSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);


}
