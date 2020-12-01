package com.jiyeyihe.cre.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpApply;
import com.jiyeyihe.cre.webapp.entity.HpApplyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HpApplyMapper extends BaseMapper<HpApply> {
    /**
     * app 查询单个我的申请信息
     * @param wrapper
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card  idCard, ha.front_id_card_url frontIdCardUrl, ha.back_id_card_url backIdCardUrl,\n" +
            "       ha.business_license_picture_url businessLicensePictureUrl, ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress, ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getAppHpApplySelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * app 我申请的信息分页查询
     * @param  userId
     * @return
     */
    @Select("select id, apply_name applyName, age, id_card idCard, front_id_card_url frontIdCardUrl, back_id_card_url backIdCardUrl,\n" +
            "business_license_picture_url businessLicensePictureUrl, current_residence_province currentResidenceProvince, current_residence_city currentResidenceCity,\n" +
            "current_residence_district currentResidenceDistrict, current_residential_address currentResidentialAddress, phone_number phoneNumber, apply_status applyStatus,\n" +
            "merchant_type merchantType, apply_rental_type applyRentalType,\n" +
            "createtime, updatetime\n" +
            "from t_hp_apply ha where ha.user_id=#{userId}\n" +
            "UNION ALL\n" +
            "select id, apply_name applyName, age, id_card idCard, front_id_card_url frontIdCardUrl, back_id_card_url backIdCardUrl,\n" +
            "business_license_picture_url businessLicensePictureUrl, current_residence_province currentResidenceProvince, current_residence_city currentResidenceCity,\n" +
            "current_residence_district currentResidenceDistrict, current_residential_address currentResidentialAddress, phone_number phoneNumber, apply_status applyStatus,\n" +
            "merchant_type merchantType, apply_rental_type applyRentalType,\n" +
            "createtime, updatetime\n" +
            "from t_ob_apply oa where oa.user_id=#{userId}\n"+
            " ${ew.customSqlSegment}")
    List<HpApplyVo> getAppHpApplyListPage(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userId") Long userId);

/*------------------------------*/

    /**
     * 平台端 查询单个我的申请信息
     * @param wrapper
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card idCard, ha.front_id_card_url frontIdCardUrl, ha.back_id_card_url backIdCardUrl,\n" +
            "       ha.business_license_picture_url businessLicensePictureUrl, ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress, ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,\n" +
            "       ha.createtime, ha.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getByIdHpApply(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 根据id查找租房申请信息
     * @param wrapper
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card idCard, ha.front_id_card_url frontIdCardUrl, ha.back_id_card_url backIdCardUrl,\n" +
            "       ha.business_license_picture_url businessLicensePictureUrl, ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress, ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,ha.user_id userId,\n" +
            "       ha.createtime, ha.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    HpApply getHpApplyById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 平台端租房  查询申请的信息记录
     * @param wrapper
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card idCard,\n" +
            "       ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress,\n" +
            "       ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,\n" +
            "       ha.createtime, ha.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<HpApplyVo> getHpApplySelectList(@Param(Constants.WRAPPER) Wrapper wrapper);



    /**
     * 商家端租房 查询单个我的申请信息
     * @param wrapper
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card idCard, ha.front_id_card_url frontIdCardUrl, ha.back_id_card_url backIdCardUrl,\n" +
            "       ha.business_license_picture_url businessLicensePictureUrl, ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress, ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getByIdHpApplySelect(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 商家端 我申请的信息分页查询
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card idCard, ha.front_id_card_url frontIdCardUrl, ha.back_id_card_url backIdCardUrl,\n" +
            "       ha.business_license_picture_url businessLicensePictureUrl, ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress, ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,\n" +
            "       ha.createtime, ha.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<HpApplyVo> getHpApplyListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 平台端  租房查询已经审核通过的用户
     * @return
     */
    @Select("select ha.id, ha.apply_name applyName, ha.age, ha.id_card idCard,\n" +
            "       ha.current_residence_province currentResidenceProvince, ha.current_residence_city currentResidenceCity,\n" +
            "       ha.current_residence_district currentResidenceDistrict, ha.current_residential_address currentResidentialAddress, ha.phone_number phoneNumber, ha.apply_status applyStatus,\n" +
            "       ha.merchant_type merchantType, ha.apply_rental_type applyRentalType,\n" +
            "       ha.createtime, ha.updatetime,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_apply ha\n" +
            "left join sys_district sd on ha.current_residence_district = sd.id\n" +
            "left join sys_city sc on ha.current_residence_city = sc.id\n" +
            "left join sys_province sp on ha.current_residence_province = sp.id\n" +
            " ${ew.customSqlSegment}")
    List<HpApplyVo> getHpApplySelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);


}
