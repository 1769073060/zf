package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ObInfoMapper extends BaseMapper<ObInfo> {

    @Select("select hi.* \n" +
            " from t_ob_info hi\n" +
            "left join t_ob_matching hm on hi.id = hm.ob_info_id\n" +
            "left join t_ob_rent hr on hi.id = hr.ob_info_id \n" +
            "left join t_ob_surrounding_facilities hsf on hi.id = hsf.ob_info_id\n" +
            " ${ew.customSqlSegment}")
    List<ObInfoVo> getObInfoDetailSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 查询最大值id
     */
    @Select("select id from t_ob_info where id = (select max(id) from t_ob_info)")
    ObInfo getObInfoDetailSelectMaxId();


    /**
     * 获取单个写字楼详情
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.ob_location_id obLocationId,hi.property_name propertyName,hi.building,hi.house_number houseNumber,\n" +
            "       hi.lease_type leaseType,hi.office_building_type officeBuildingType,hi.office_level officeLevel,hi.nature,\n" +
            "       hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.office_description officeDescription,\n" +
            "       hi.floor,hi.total_floor totalFloor,hi.construction_area constructionArea,hi.usage_rate usageRate,hi.renovation_condition renovationCondition,\n" +
            "       hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,hi.lease_term_type leaseTermType,\n" +
            "       hi.rental_day rentalDay,hi.rental_month rentalMonth,hi.rent_free_period rentFreePeriod,hi.start_station startStation,hi.end_station endStation,\n" +
            "       hi.registered,hi.segmentation,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "       hm.elevator,hm.water,hm.electric,hm.air_conditioner airConditioner,hm.office_furniture officeFurniture,hm.central_heating centralHeating,\n" +
            "       hm.staff_canteen staffCanteen,hm.security_monitoring securityMonitoring,hm.free_parking freeParking,hm.cable_tv cableTv,hm.broadband,\n" +
            "       hr.property_fee propertyFee,hr.parking_fee parkingFee,hr.cable_bill cableBill,\n" +
            "       hsf.super_store superStore,hsf.school,hsf.express_way expressWay,hsf.hospital,hsf.shopping_mall shoppingMall,hsf.park,\n" +
            "       hsf.bullet_train bulletTrain,hsf.bank,hsf.business_circle businessCircle\n" +
            " from t_ob_info hi\n" +
            "left join t_ob_matching hm on hi.id = hm.ob_info_id\n" +
            "left join t_ob_rent hr on hi.id = hr.ob_info_id\n" +
            "left join t_ob_surrounding_facilities hsf on hi.id = hsf.ob_info_id" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getObInfoDetailSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);



    @Select("select hl.id,hl.district,hl.property_name from t_ob_location hl" +
            " ${ew.customSqlSegment}")
    List<ObLocation> getObInfoDetailListPropertyName(@Param(Constants.WRAPPER) Wrapper wrapper);



    /**
     * 小程序写字楼查询首页列表展示
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hl.property_name propertyName,hi.rental_day rentalDay,hi.rental_month  rentalMonth,\n" +
            "       hi.construction_area constructionArea,hi.office_building_type officeBuildingType,\n" +
            "       hi.house_orientation_type houseOrientationType,hi.carousel_url carouselUrl,hi.construction_area constructionArea,hi.createtime,\n" +
            "       hl.longitude,hl.latitude,hres.updatetime,\n" +
            "       hres.ob_info_id obResourcesId,hres.updatetime,\n" +
            "       sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_name officeBuildingTypeName,\n" +
            "       sd3.dic_name natureTypeName,\n" +
            "       sds.district_name districtName,\n" +
            "       ROUND(6378.138 * 2 * ASIN\n" +
            "       (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - hl.latitude * PI() / 180) / 2),2) +\n" +
            "       COS(#{userLatitude} * PI() / 180) * COS(hl.latitude * PI() / 180) *\n" +
            "       POW(SIN((#{userLongitude} * PI() / 180 - hl.longitude * PI() / 180) / 2),2))) * 1000) AS distance\n" +
            "from t_ob_info hi\n" +
            "left join t_ob_resources hres on hi.id = hres.ob_info_id\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join sys_district sds on hl.district = sds.id\n"+
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.office_building_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.nature = sd3.dic_value\n"+
            " ${ew.customSqlSegment}")
    List<ObInfoVo> getAppObInfoDetailSelectList(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude);



    /**
     * app获取userId 单个写字楼详情
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.ob_location_id obLocationId,hi.property_name propertyName,hi.building,hi.house_number houseNumber,hi.business_id businessId,\n" +
            "hi.lease_type leaseType,hi.office_building_type officeBuildingType,hi.office_level officeLevel,hi.nature,\n" +
            "hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.office_description officeDescription,\n" +
            "hi.floor,hi.total_floor totalFloor,hi.floor_height floorHeight,hi.construction_area constructionArea,hi.usage_rate usageRate,\n" +
            "hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "hi.lease_term_type leaseTermType,hi.rental_day rentalDay,hi.rental_month rentalMonth,hi.rent_free_period rentFreePeriod,\n" +
            "hi.start_station startStation,hi.end_station endStation,hi.registered,hi.segmentation,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "hfh.attention_status attentionStatus,hah.appointment_status appointmentStatus,\n" +
            "hl.property_name propertyName,hl.district,hl.address,hl.office_descrip officeDescrip,hl.longitude,hl.latitude,\n" +
            "hm.elevator,hm.water,hm.electric,hm.air_conditioner airConditioner,hm.office_furniture officeFurniture,hm.central_heating centralHeating,\n" +
            "hm.staff_canteen staffCanteen,hm.security_monitoring securityMonitoring,hm.free_parking freeParking,hm.cable_tv cableTv,hm.broadband,\n" +
            "hr.property_fee propertyFee,hr.parking_fee parkingFee,hr.cable_bill cableBill,\n" +
            "hsf.super_store superStore,hsf.school,hsf.express_way expressWay,hsf.hospital,hsf.shopping_mall shoppingMall,hsf.park,hsf.bullet_train bulletTrain,hsf.bank,hsf.business_circle businessCircle,\n" +
            "sd1.dic_value renovationConditionTypeValue,sd1.dic_name renovationConditionTypeName,\n" +
            "sd2.dic_value houseOrientationTypeValue,sd2.dic_name houseOrientationTypeName,\n" +
            "sd3.dic_value payWayTypeValue,sd3.dic_name payWayTypeName,\n" +
            "sd4.dic_value floorHeightTypeValue,sd4.dic_name floorHeightTypeName,\n" +
            "sd5.dic_value officeBuildingTypeValue,sd5.dic_name officeBuildingTypeName,\n" +
            "sd6.dic_value officeLevelTypeValue,sd6.dic_name officeLevelTypeName,\n" +
            "sd8.dic_value leaseTermTypeValue,sd8.dic_name leaseTermTypeName,\n" +
            "sd9.dic_value rentFreePeriodTypeValue,sd9.dic_name rentFreePeriodTypeName,\n" +
            "sd10.dic_value registeredTypeValue,sd10.dic_name registeredTypeName,\n" +
            "sd11.dic_name segmentationTypeName\n" +
            "from   t_ob_info hi\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join t_ob_matching hm on hi.id = hm.ob_info_id\n" +
            "left join t_ob_rent hr on hi.id = hr.ob_info_id\n" +
            "left join t_ob_surrounding_facilities hsf on  hi.id = hsf.ob_info_id\n" +
            "left join t_ob_follow_house hfh on  hi.id = hfh.ob_info_id\n" +
            "left join t_ob_appointment_house hah on hi.id = hah.ob_info_id\n" +
            "left join sys_dictionary sd1 on hi.renovation_condition = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.house_orientation_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.pay_way_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on hi.floor_height = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on hi.office_building_type = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on hi.office_level = sd6.dic_value\n" +
            "left join sys_dictionary sd8 on hi.lease_term_type = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on hi.rent_free_period = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on hi.registered = sd10.dic_value\n" +
            "left join sys_dictionary sd11 on  hi.segmentation = sd11.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppObInfoSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**  /**
     * app获取单个写字楼详情
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.ob_location_id obLocationId,hi.property_name propertyName,hi.building,hi.house_number houseNumber,hi.business_id businessId,\n" +
            "       hi.lease_type leaseType,hi.office_building_type officeBuildingType,hi.office_level officeLevel,hi.nature,\n" +
            "       hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.office_description officeDescription,\n" +
            "       hi.floor,hi.total_floor totalFloor,hi.floor_height floorHeight,hi.construction_area constructionArea,hi.usage_rate usageRate,\n" +
            "       hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "       hi.lease_term_type leaseTermType,hi.rental_day rentalDay,hi.rental_month rentalMonth,hi.rent_free_period rentFreePeriod,\n" +
            "       hi.start_station startStation,hi.end_station endStation,hi.registered,hi.segmentation,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "       hl.property_name propertyName,hl.district,hl.address,hl.office_descrip officeDescrip,hl.longitude,hl.latitude,\n" +
            "       hm.elevator,hm.water,hm.electric,hm.air_conditioner airConditioner,hm.office_furniture officeFurniture,hm.central_heating centralHeating,\n" +
            "       hm.staff_canteen staffCanteen,hm.security_monitoring securityMonitoring,hm.free_parking freeParking,hm.cable_tv cableTv,hm.broadband,\n" +
            "       hr.property_fee propertyFee,hr.parking_fee parkingFee,hr.cable_bill cableBill,\n" +
            "       hsf.super_store superStore,hsf.school,hsf.express_way expressWay,hsf.hospital,hsf.shopping_mall shoppingMall,hsf.park,hsf.bullet_train bulletTrain,hsf.bank,hsf.business_circle businessCircle,\n" +
            "       sd1.dic_value renovationConditionTypeValue,sd1.dic_name renovationConditionTypeName,\n" +
            "       sd2.dic_value houseOrientationTypeValue,sd2.dic_name houseOrientationTypeName,\n" +
            "       sd3.dic_value payWayTypeValue,sd3.dic_name payWayTypeName,\n" +
            "       sd4.dic_value floorHeightTypeValue,sd4.dic_name floorHeightTypeName,\n" +
            "       sd5.dic_value officeBuildingTypeValue,sd5.dic_name officeBuildingTypeName,\n" +
            "       sd6.dic_value officeLevelTypeValue,sd6.dic_name officeLevelTypeName,\n" +
            "       sd7.dic_value natureTypeValue,sd7.dic_name natureTypeName,\n" +
            "       sd8.dic_value leaseTermTypeValue,sd8.dic_name leaseTermTypeName,\n" +
            "       sd9.dic_value rentFreePeriodTypeValue,sd9.dic_name rentFreePeriodTypeName,\n" +
            "       sd10.dic_value registeredTypeValue,sd10.dic_name registeredTypeName,\n" +
            "       sd11.dic_value segmentationTypeValue,sd11.dic_name segmentationTypeName\n" +
            "from   t_ob_info hi\n" +
            "left join t_ob_location hl on hl.id = hi.ob_location_id\n" +
            "left join t_ob_matching hm on hm.ob_info_id = hi.id\n" +
            "left join t_ob_rent hr on hr.ob_info_id = hi.id\n" +
            "left join t_ob_surrounding_facilities hsf on hsf.ob_info_id = hi.id\n" +
            "left join sys_dictionary sd1 on hi.renovation_condition = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.house_orientation_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.pay_way_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on sd4.dic_value = hi.floor_height\n" +
            "left join sys_dictionary sd5 on sd5.dic_value = hi.office_building_type\n" +
            "left join sys_dictionary sd6 on sd6.dic_value = hi.office_level\n" +
            "left join sys_dictionary sd7 on sd7.dic_value = hi.nature\n" +
            "left join sys_dictionary sd8 on sd8.dic_value = hi.lease_term_type\n" +
            "left join sys_dictionary sd9 on sd9.dic_value = hi.rent_free_period\n" +
            "left join sys_dictionary sd10 on sd10.dic_value = hi.registered\n" +
            "left join sys_dictionary sd11 on sd11.dic_value = hi.segmentation\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppObInfoIdSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 小程序多条件查询首页列表展示
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hl.property_name propertyName,hi.rental_day rentalDay,hi.rental_month  rentalMonth,\n" +
            "       hi.construction_area constructionArea,hi.office_building_type officeBuildingType,hl.longitude,hl.latitude,\n" +
            "       hi.house_orientation_type houseOrientationType,hi.carousel_url carouselUrl,hi.vr_pictures_url vr_picturesUrl,\n" +
            "       hres.ob_info_id obResourcesId,hres.office_resources_status officeResourcesStatus,\n" +
            "       sd.district_name districtName,sd.id districtId,sc.city_name cityName,sc.id cityId, sp.province_name provinceName,sp.id provinceId ,\n" +
            "       sd1.dic_value houseOrientationTypeValue,sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_value leaseTypeValue,sd2.dic_name leaseTypeName,\n" +
            "       sd3.dic_value officeBuildingTypeValue,sd3.dic_name officeBuildingTypeName,\n" +
            "       sd4.dic_value natureTypeValue,sd4.dic_name natureTypeName,\n" +
            "       sd5.dic_value floorHeightTypeValue,sd5.dic_name floorHeightTypeName,\n" +
            "       sd6.dic_value registeredTypeValue,sd6.dic_name registeredTypeName,\n" +
            "       sd7.dic_value segmentationTypeValue,sd7.dic_name segmentationTypeName,\n" +
            "       ROUND(6378.138 * 2 * ASIN\n" +
            "       (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - hl.latitude * PI() / 180) / 2),2) +\n" +
            "       COS(#{userLatitude} * PI() / 180) * COS(hl.latitude * PI() / 180) *\n" +
            "       POW(SIN((#{userLongitude} * PI() / 180 - hl.longitude * PI() / 180) / 2),2))) * 1000) AS distance\n" +
            "from t_ob_info hi\n" +
            "left join t_ob_resources hres on hi.id = hres.ob_info_id\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.lease_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.office_building_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on hi.nature = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on hi.floor_height = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on hi.registered = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on hi.segmentation = sd7.dic_value\n" +
            " ${ew.customSqlSegment}")
    List<ObInfoVo> getAppObResourceSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude);

    /**
     * app写字楼查询 有没有存在这个关注表里面房屋的信息
     * @param wrapper
     * @return
     */
    @Select("select hfh.id,hfh.user_id userId,hfh.ob_info_id,hfh.attention_status attentionStatus from t_ob_follow_house hfh ${ew.customSqlSegment}")
    ObFollowHouse getAppObResourceFollowStatusById(@Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("select hi.id  from t_ob_info hi ${ew.customSqlSegment}")
    ObInfo getAppObResourceAttentionById(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select hfh.id,hfh.user_id userId,hfh.ob_info_id,hfh.attention_status attentionStatus from t_ob_follow_house hfh ${ew.customSqlSegment}")
    ObFollowHouse getAppObInfoFollowById(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 小程序我关注的写字楼信息展示
     * @param wrapper
     * @return
     */
    @Select("select hfh.attention_status attentionStatus,hi.construction_area constructionArea,hfh.user_id userId,hfh.appointment_status,\n" +
            "       hi.id,hl.property_name propertyName,hi.lease_type leaseType,\n" +
            "       hi.house_orientation_type houseOrientationType,hi.rental_day rentalDday,hi.rental_month rentalMonth,hi.carousel_url carouselUrl,hi.vr_pictures_url vr_picturesUrl,\n" +
            "       hl.longitude,hl.latitude,\n" +
            "       hres.ob_info_id obResourcesId,hres.office_resources_status officeResourcesStatus,\n" +
            "       sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_name officeBuildingTypeName,\n" +
            "       sd3.dic_name natureTypeName,\n" +
            "       ROUND(6378.138 * 2 * ASIN\n" +
            "       (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - hl.latitude * PI() / 180) / 2),2) +\n" +
            "       COS(#{userLatitude} * PI() / 180) * COS(hl.latitude * PI() / 180) *\n" +
            "       POW(SIN((#{userLongitude} * PI() / 180 - hl.longitude * PI() / 180) / 2),2))) * 1000) AS distance\n" +
            "from t_ob_follow_house hfh\n" +
            "left join t_ob_info hi on hfh.ob_info_id = hi.id\n" +
            "left join t_ob_resources hres on hi.id = hres.ob_info_id\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.office_building_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.nature = sd3.dic_value" +
            " ${ew.customSqlSegment}")
    List<ObInfoVo> getAppObPersonalCentreListPage(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude);


    /**
     * 小程序写字楼看房记录列表展示
     * @param wrapper
     * @return
     */
    @Select("select hah.id obAppointmentHouseId,\n" +
            "       hcvr.id obCustomerViewingRecordId,hcvr.view_house_time viewHouseTime,hcvr.viewing_status viewingStatus,hcvr.payment_status paymentStatus,\n" +
            "       hi.id,hi.business_id businessId,hi.lease_type leaseType,hi.house_orientation_type houseOrientationType,hi.rental_month rentalMonth," +
            "       hi.rental_day rentalDay,hi.carousel_url carouselUrl,hi.vr_pictures_url vr_picturesUrl,hi.construction_area constructionArea,\n" +
            "       hl.property_name propertyName,hl.longitude,hl.latitude,\n" +
            "       sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_name officeBuildingTypeName,\n" +
            "       sd3.dic_name natureTypeName\n" +
            "from t_ob_customer_viewing_record hcvr\n" +
            "left join t_ob_info hi on hcvr.ob_info_id = hi.id\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join t_ob_appointment_house hah on hcvr.ob_info_id = hah.ob_info_id\n" +
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.office_building_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.nature = sd3.dic_value" +
            " ${ew.customSqlSegment}")
    List<ObInfoVo> getAppObCustomerViewingRecordSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 小程序看房记录单个房屋详情
     * 获取客户看房记录的infoid，查询
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.ob_location_id obLocationId,hi.property_name propertyName,hi.building,hi.house_number houseNumber,hi.business_id businessId,\n" +
            "hi.lease_type leaseType,hi.office_building_type officeBuildingType,hi.office_level officeLevel,hi.nature,\n" +
            "hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.office_description officeDescription,\n" +
            "hi.floor,hi.total_floor totalFloor,hi.floor_height floorHeight,hi.construction_area constructionArea,hi.usage_rate usageRate,\n" +
            "hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "hi.lease_term_type leaseTermType,hi.rental_day rentalDay,hi.rental_month rentalMonth,hi.rent_free_period rentFreePeriod,\n" +
            "hi.start_station startStation,hi.end_station endStation,hi.registered,hi.segmentation,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "hfh.attention_status attentionStatus,hah.appointment_status appointmentStatus,\n" +
            "hl.property_name propertyName,hl.district,hl.address,hl.office_descrip officeDescrip,hl.longitude,hl.latitude,\n" +
            "hm.elevator,hm.water,hm.electric,hm.air_conditioner airConditioner,hm.office_furniture officeFurniture,hm.central_heating centralHeating,\n" +
            "hm.staff_canteen staffCanteen,hm.security_monitoring securityMonitoring,hm.free_parking freeParking,hm.cable_tv cableTv,hm.broadband,\n" +
            "hr.property_fee propertyFee,hr.parking_fee parkingFee,hr.cable_bill cableBill,\n" +
            "hsf.super_store superStore,hsf.school,hsf.express_way expressWay,hsf.hospital,hsf.shopping_mall shoppingMall,hsf.park,hsf.bullet_train bulletTrain,hsf.bank,hsf.business_circle businessCircle,\n" +
            "sd1.dic_value renovationConditionTypeValue,sd1.dic_name renovationConditionTypeName,\n" +
            "sd2.dic_value houseOrientationTypeValue,sd2.dic_name houseOrientationTypeName,\n" +
            "sd3.dic_value payWayTypeValue,sd3.dic_name payWayTypeName,\n" +
            "sd4.dic_value floorHeightTypeValue,sd4.dic_name floorHeightTypeName,\n" +
            "sd5.dic_value officeBuildingTypeValue,sd5.dic_name officeBuildingTypeName,\n" +
            "sd6.dic_value officeLevelTypeValue,sd6.dic_name officeLevelTypeName,\n" +
            "sd8.dic_value leaseTermTypeValue,sd8.dic_name leaseTermTypeName,\n" +
            "sd9.dic_value rentFreePeriodTypeValue,sd9.dic_name rentFreePeriodTypeName,\n" +
            "sd10.dic_value registeredTypeValue,sd10.dic_name registeredTypeName,\n" +
            "sd11.dic_name segmentationTypeName\n" +
            "from   t_ob_info hi\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join t_ob_matching hm on hi.id = hm.ob_info_id\n" +
            "left join t_ob_rent hr on hi.id = hr.ob_info_id\n" +
            "left join t_ob_surrounding_facilities hsf on  hi.id = hsf.ob_info_id\n" +
            "left join t_ob_follow_house hfh on  hi.id = hfh.ob_info_id\n" +
            "left join t_ob_appointment_house hah on hi.id = hah.ob_info_id\n" +
            "left join sys_dictionary sd1 on hi.renovation_condition = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.house_orientation_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.pay_way_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on hi.floor_height = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on hi.office_building_type = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on hi.office_level = sd6.dic_value\n" +
            "left join sys_dictionary sd8 on hi.lease_term_type = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on hi.rent_free_period = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on hi.registered = sd10.dic_value\n" +
            "left join sys_dictionary sd11 on  hi.segmentation = sd11.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppObCustomerViewingRecordById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 小程序看房记录单个订单详情
     * 获取客户看房记录的infoid，查询
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.ob_location_id obLocationId,hi.property_name propertyName,hi.building,hi.house_number houseNumber,hi.business_id businessId,\n" +
            "       hi.lease_type leaseType,hi.office_building_type officeBuildingType,hi.office_level officeLevel,hi.nature,\n" +
            "      hi.office_description officeDescription,\n" +
            "       hi.floor,hi.total_floor totalFloor,hi.floor_height floorHeight,hi.construction_area constructionArea,hi.usage_rate usageRate,\n" +
            "       hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "       hi.lease_term_type leaseTermType,hi.rental_day rentalDay,hi.rental_month rentalMonth,hi.rent_free_period rentFreePeriod,\n" +
            "       hi.start_station startStation,hi.end_station endStation,hi.registered,hi.segmentation,hi.agency_fee agencyFee,hi.service_fee serviceFee,\n" +
            "       hl.property_name propertyName,hl.district,hl.address,hl.office_descrip officeDescrip,hl.longitude,hl.latitude,\n" +
            "       sd.district_name districtName,sc.city_name cityName, sp.province_name provinceName,\n" +
            "       sd1.dic_value renovationConditionTypeValue,sd1.dic_name renovationConditionTypeName,\n" +
            "       sd2.dic_value payWayTypeValue,sd2.dic_name payWayTypeName,\n" +
            "       sd3.dic_value floorHeightTypeValue,sd3.dic_name floorHeightTypeName,\n" +
            "       sd4.dic_value officeBuildingTypeValue,sd4.dic_name officeBuildingTypeName,\n" +
            "       sd5.dic_value officeLevelTypeValue,sd5.dic_name officeLevelTypeName,\n" +
            "       sd6.dic_value natureTypeValue,sd6.dic_name natureTypeName,\n" +
            "       sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "       sd8.dic_value rentFreePeriodTypeValue,sd8.dic_name rentFreePeriodTypeName,\n" +
            "       sd9.dic_value registeredTypeValue,sd9.dic_name registeredTypeName,\n" +
            "       sd10.dic_value segmentationTypeValue,sd10.dic_name segmentationTypeName\n" +
            "from   t_ob_info hi\n" +
            "left join t_ob_customer_viewing_record hcvr on hcvr.ob_info_id = hi.id\n" +
            "left join t_ob_location hl on hl.id = hi.ob_location_id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd1 on hi.renovation_condition = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.pay_way_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.floor_height = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on hi.office_building_type = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on hi.office_level = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on hi.nature = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on hi.lease_term_type = sd7.dic_value\n" +
            "left join sys_dictionary sd8 on hi.rent_free_period = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on hi.registered = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on hi.segmentation = sd10.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppObOrderById(@Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("SELECT\n" +
            "    ROUND(6378.138 * 2 * ASIN\n" +
            "    (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - #{houseLatitude} * PI() / 180) / 2),2) +\n" +
            "    COS(#{userLatitude} * PI() / 180) * COS(#{houseLatitude} * PI() / 180) *\n" +
            "    POW(SIN((#{userLongitude} * PI() / 180 - #{houseLongitude} * PI() / 180) / 2),2))) * 1000)\n" +
            "AS distance " +
            "")
    Map<String,Object> getIdLongitudeLatitude(@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude,@Param("houseLongitude") Double houseLongitude,@Param("houseLatitude") Double houseLatitude);


    @Select("select oi.id,oi.rental_month rentalMonth from t_ob_info oi ${ew.customSqlSegment}")
    ObInfo getAppObInfoOrderSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

}
