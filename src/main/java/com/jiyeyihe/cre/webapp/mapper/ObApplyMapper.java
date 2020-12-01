package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import com.jiyeyihe.cre.webapp.entity.ObApplyVo;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ObApplyMapper extends BaseMapper<ObApply> {


    /**
     * 平台端 查询单个我的申请信息
     * @param wrapper
     * @return
     */
    @Select("select oa.id, oa.apply_name applyName, oa.age,oa.id_card idCard, oa.front_id_card_url frontIdCardUrl, oa.back_id_card_url backIdCardUrl,\n" +
            "       oa.business_license_picture_url businessLicensePictureUrl, oa.current_residence_province currentResidenceProvince, oa.current_residence_city currentResidenceCity,\n" +
            "       oa.current_residence_district currentResidenceDistrict, oa.current_residential_address currentResidentialAddress, oa.phone_number phoneNumber, oa.apply_status applyStatus,\n" +
            "       oa.merchant_type merchantType, oa.apply_rental_type applyRentalType,\n" +
            "       oa.createtime, oa.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_apply oa\n" +
            "left join sys_district sd on oa.current_residence_district = sd.id\n" +
            "left join sys_city sc on oa.current_residence_city = sc.id\n" +
            "left join sys_province sp on oa.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getByIdObApply(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 根据id查找申请信息
     * @param wrapper
     * @return
     */
    @Select("select oa.id, oa.apply_name applyName, oa.age, oa.id_card idCard, oa.front_id_card_url frontIdCardUrl, oa.back_id_card_url backIdCardUrl,\n" +
            "       oa.business_license_picture_url businessLicensePictureUrl, oa.current_residence_province currentResidenceProvince, oa.current_residence_city currentResidenceCity,\n" +
            "       oa.current_residence_district currentResidenceDistrict, oa.current_residential_address currentResidentialAddress, oa.phone_number phoneNumber, oa.apply_status applyStatus,\n" +
            "       oa.merchant_type merchantType, oa.apply_rental_type applyRentalType,oa.user_id userId,\n" +
            "       oa.createtime, oa.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_apply oa\n" +
            "left join sys_district sd on oa.current_residence_district = sd.id\n" +
            "left join sys_city sc on oa.current_residence_city = sc.id\n" +
            "left join sys_province sp on oa.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    ObApply getObApplyById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 平台端写字楼  查询申请的信息记录
     * @param wrapper
     * @return
     */
    @Select("select oa.id, oa.apply_name applyName, oa.age, oa.id_card  idCard,\n" +
            "       oa.current_residence_province currentResidenceProvince, oa.current_residence_city currentResidenceCity,\n" +
            "       oa.current_residence_district currentResidenceDistrict, oa.current_residential_address currentResidentialAddress,\n" +
            "       oa.phone_number phoneNumber, oa.apply_status applyStatus,\n" +
            "       oa.merchant_type merchantType, oa.apply_rental_type applyRentalType,\n" +
            "       oa.createtime, oa.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_apply oa\n" +
            "left join sys_district sd on oa.current_residence_district = sd.id\n" +
            "left join sys_city sc on oa.current_residence_city = sc.id\n" +
            "left join sys_province sp on oa.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<ObApplyVo> getObApplySelectList(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 商家端 写字楼查询单个我的申请信息
     * @param wrapper
     * @return
     */
    @Select("select oa.business_id,oa.id,oa.apply_name applyName, oa.age, oa.id_card idCard, oa.front_id_card_url frontIdCardUrl, oa.back_id_card_url backIdCardUrl,\n" +
            "       oa.business_license_picture_url businessLicensePictureUrl, oa.current_residence_province currentResidenceProvince, oa.current_residence_city currentResidenceCity,\n" +
            "       oa.current_residence_district currentResidenceDistrict, oa.current_residential_address currentResidentialAddress, oa.phone_number phoneNumber, oa.apply_status applyStatus,\n" +
            "       oa.merchant_type merchantType, oa.apply_rental_type applyRentalType,\n" +
            "       oa.createtime, oa.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_apply oa\n" +
            "left join sys_district sd on oa.current_residence_district = sd.id\n" +
            "left join sys_city sc on oa.current_residence_city = sc.id\n" +
            "left join sys_province sp on oa.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getObApplySelectById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 商家端 查询申请的信息记录
     * @param wrapper
     * @return
     */
    @Select("select oa.id, oa.apply_name applyName, oa.age, oa.id_card idCard,\n" +
            "       oa.current_residence_province currentResidenceProvince, oa.current_residence_city currentResidenceCity,\n" +
            "       oa.current_residence_district currentResidenceDistrict, oa.current_residential_address currentResidentialAddress,\n" +
            "       oa.phone_number phoneNumber, oa.apply_status applyStatus,\n" +
            "       oa.merchant_type merchantType, oa.apply_rental_type applyRentalType,\n" +
            "       oa.createtime, oa.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_apply oa\n" +
            "left join sys_district sd on oa.current_residence_district = sd.id\n" +
            "left join sys_city sc on oa.current_residence_city = sc.id\n" +
            "left join sys_province sp on oa.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<ObApplyVo> getObApplyListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 平台端 写字楼查询申请的信息记录
     * @param wrapper
     * @return
     */
    @Select("select oa.id, oa.apply_name applyName, oa.age, oa.id_card idCard,\n" +
            "       oa.current_residence_province currentResidenceProvince, oa.current_residence_city currentResidenceCity,\n" +
            "       oa.current_residence_district currentResidenceDistrict, oa.current_residential_address currentResidentialAddress,\n" +
            "       oa.phone_number phoneNumber, oa.apply_status applyStatus,\n" +
            "       oa.merchant_type merchantType, oa.apply_rental_type applyRentalType,\n" +
            "       oa.createtime, oa.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_apply oa\n" +
            "left join sys_district sd on oa.current_residence_district = sd.id\n" +
            "left join sys_city sc on oa.current_residence_city = sc.id\n" +
            "left join sys_province sp on oa.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<ObApplyVo> getObApplySelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

}
