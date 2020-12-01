package com.jiyeyihe.cre.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpLocationVo;
import com.jiyeyihe.cre.webapp.entity.ObLocation;
import com.jiyeyihe.cre.webapp.entity.ObLocationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ObLocationMapper extends BaseMapper<ObLocation> {
    /**
     * 房产区域分页查询
     * @param wrapper
     * @return
     */
    @Select("select hl.*,sd.district_name districtName,sd.short_name districtShortName,sd.id districtId,\n" +
            "sc.city_name cityName,sc.short_name cityShortName,sc.id cityId,\n" +
            "sp.province_name provinceName,sp.short_name provinceShortName,sp.id provinceId\n" +
            "from t_ob_location hl\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<ObLocationVo> getObLocationSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 查询单个房产区域
     * @param wrapper
     * @return
     */
    @Select("select hl.id,hl.district,hl.property_name propertyName,hl.address,hl.office_descrip officeDescrip," +
            "hl.updatetime,hl.createtime,hl.longitude,hl.latitude,\n" +
            "sd.district_name districtName,sd.short_name districtShortName,sd.id districtId,\n" +
            "sc.city_name cityName,sc.short_name cityShortName,sc.id cityId,\n" +
            "sp.province_name provinceName,sp.short_name provinceShortName,sp.id provinceId\n" +
            "from t_ob_location hl \n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getObLocationSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

}
