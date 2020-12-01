package com.jiyeyihe.cre.webapp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpFollowHouse;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface HpInfoMapper extends BaseMapper<HpInfo> {


    @Select("select hi.id,hi.hp_location_id hpLocationId,hi.community_name,hi.house_number,hi.lease_type,hi.viewing_time," +
            "hi.checkin_time,hi.carousel_url carouselUrl,hi.vr_pictures_url,hi.vr_pictures_url,hi.video_url,hi.house_description,hi.bedroom,hi.drawing_room," +
            "hi.toilet,hi.floor,hi.total_floor,hi.house_area,hi.renovation_condition,hi.house_orientation_type,hi.pay_way_type," +
            "hi.lease_term_type,hi.rental,hi.bedroom_type,hi.bedroom_facing_type ,hi.square_meter,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee," +
            "hf.hp_info_id hpFurnitureId,hf.broadband,hf.wardrobe,hf.television,hf.washing_machine washingMachine,hf.heating," +
            "hf.heater,hf.bed,hf.sofa,hf.air_conditioner airConditioner,hf.refrigerator,hf.balcony,hf.private_toilet privateToilet,hf.mattress," +
            "hf.bedside_cupboard bedsideCupboard,hf.smart_lock smartLock,hf.tea_table teaTable,hf.dining_table diningTable,hf.computer_desk computerDesk," +
            "hf.gas_stoves gasStoves,hf.range_hood rangeHood," +
            "hr.hp_info_id hpRentId,hr.management_fee managementFee," +
            "hr.gas_fee gasFee,hr.heating_fee heatingFee,hr.cable_bill cableBill,hr.parking_fee parkingFee," +
            "hrs.hp_info_id hpRequirementsId,hrs.girls_only girlsOnly,hrs.no_pets noPets,hrs.normal_schedule normalSchedule,hrs.no_smoking noSmoking" +
            " from t_hp_info hi\n" +
            "left join t_hp_furniture hf on hi.id = hf.hp_info_id\n" +
            "left join t_hp_rent hr on hi.id = hr.hp_info_id\n" +
            "left join t_hp_requirements hrs on hi.id = hrs.hp_info_id" +
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getHpInfoDetailSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);



    /**
     * 获取小区初始化下拉框
     * @return
     */
    @Select("select hl.id,hl.district,hl.community_name,hl.building from t_hp_location hl" +
            " ${ew.customSqlSegment}")
    List<HpLocation> getHpInfoDetailSelectCommunityName(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 查询最大值id
     */
    @Select("select id from t_hp_info where id = (select max(id) from t_hp_info)")
    HpInfo getHpInfoDetailSelectMaxId();


    /**
     * 获取单个房屋详情
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.hp_location_id hpLocationId,hi.community_name communityName,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime," +
            "hi.checkin_time checkinTime,hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom," +
            "hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType," +
            "hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee," +
            "hf.hp_info_id hpFurnitureId,hf.broadband,hf.wardrobe,hf.television,hf.washing_machine washingMachine,hf.heating," +
            "hf.heater,hf.bed,hf.sofa,hf.air_conditioner airConditioner,hf.refrigerator,hf.balcony,hf.private_toilet privateToilet,hf.mattress," +
            "hf.bedside_cupboard bedsideCupboard,hf.smart_lock smartLock,hf.tea_table teaTable,hf.dining_table diningTable,hf.computer_desk computerDesk," +
            "hf.gas_stoves gasStoves,hf.range_hood rangeHood," +
            "hr.hp_info_id hpRentId,hr.management_fee managementFee," +
            "hr.gas_fee gasFee,hr.heating_fee heatingFee,hr.cable_bill cableBill,hr.parking_fee parkingFee," +
            "hrs.hp_info_id hpRequirementsId,hrs.girls_only girlsOnly,hrs.no_pets noPets,hrs.normal_schedule normalSchedule,hrs.no_smoking noSmoking" +
            " from t_hp_info hi\n" +
            "left join t_hp_furniture hf on hi.id = hf.hp_info_id\n" +
            "left join t_hp_rent hr on hi.id = hr.hp_info_id\n" +
            "left join t_hp_requirements hrs on hi.id = hrs.hp_info_id" +
            " ${ew.customSqlSegment}")
    Map<String,Object> getHpInfoDetailSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 小程序查询首页列表展示
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hl.community_name communityName,hi.lease_type leaseType,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "       hi.toilet,hi.house_area houseArea,hi.house_orientation_type houseOrientationType,hi.rental,hi.carousel_url carouselUrl,hi.vr_pictures_url vr_picturesUrl, hi.quantity_status quantityStatus,\n" +
            "       hl.id hpLocationId,hl.longitude,hl.latitude,\n" +
            "       hres.hp_info_id hpResourcesId,hres.house_resources_status houseResourcesStatus,hres.updatetime,\n" +
            "       sd.dic_value leaseTypeValue,sd.dic_name leaseTypeName,\n" +
            "       sd1.dic_value houseOrientationTypeValue,sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_value bedroomTypeValue,sd2.dic_name bedroomTypeName,\n" +
            "       sd3.dic_value bedroomFacingTypeValue,sd3.dic_name bedroomFacingTypeName,\n" +
            "       sds.district_name districtName,\n" +
            "       ROUND(6378.138 * 2 * ASIN\n" +
            "       (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - hl.latitude * PI() / 180) / 2),2) +\n" +
            "       COS(#{userLatitude} * PI() / 180) * COS(hl.latitude * PI() / 180) *\n" +
            "       POW(SIN((#{userLongitude} * PI() / 180 - hl.longitude * PI() / 180) / 2),2))) * 1000) AS distance\n" +
            "from t_hp_info hi\n" +
            "left join t_hp_resources hres on hi.id = hres.hp_info_id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_district sds on hl.district = sds.id\n"+
            "left join sys_dictionary sd on sd.dic_value = hi.lease_type\n" +
            "left join sys_dictionary sd1 on sd1.dic_value = hi.house_orientation_type\n" +
            "left join sys_dictionary sd2 on sd2.dic_value = hi.bedroom_type\n" +
            "left join sys_dictionary sd3 on sd3.dic_value = hi.bedroom_facing_type\n" +
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getAppHpInfoDetailSelectList(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude);

    /**
     * 小程序发布我的房源获取单个房屋详情
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.hp_location_id hpLocationId,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime,\n" +
            "           hi.checkin_time checkinTime,hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "           hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "           hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.house_description houseDescription,\n" +
            "           hi.floor_height floorHeight,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "           hres.id hpResourcesId,hres.hp_info_id hpResourcesInfoId,hres.house_resources_status houseResourcesStatus,\n" +
            "           hl.community_name communityName,hl.building,hl.descrip,hl.address,hl.elevator,hl.parking_space parkingSpace,hl.longitude,hl.latitude,\n" +
            "           sds.district_name districtName,sc.city_name cityName, sp.province_name provinceName ,\n" +
            "           hf.hp_info_id hpFurnitureId,hf.broadband,hf.wardrobe,hf.television,hf.washing_machine washingMachine,hf.heating,\n" +
            "           hf.heater,hf.bed,hf.sofa,hf.air_conditioner airConditioner,hf.refrigerator,hf.balcony,hf.private_toilet privateToilet,hf.mattress,\n" +
            "           hf.bedside_cupboard bedsideCupboard,hf.smart_lock smartLock,hf.tea_table teaTable,hf.dining_table diningTable,hf.computer_desk computerDesk,\n" +
            "           hf.gas_stoves gasStoves,hf.range_hood rangeHood,\n" +
            "           hr.hp_info_id hpRentId,hr.management_fee managementFee,\n" +
            "           hr.gas_fee gasFee,hr.heating_fee heatingFee,hr.cable_bill cableBill,hr.parking_fee parkingFee,\n" +
            "           hrs.hp_info_id hpRequirementsId,hrs.girls_only girlsOnly,hrs.no_pets noPets,hrs.normal_schedule normalSchedule,hrs.no_smoking noSmoking,\n" +
            "           sd.dic_value viewingTimeValue,sd.dic_name viewingTimeName,\n" +
            "           sd2.dic_value checkinTimeValue,sd2.dic_name checkinTimeName,\n" +
            "           sd3.dic_value leaseTypeValue,sd3.dic_name leaseTypeName,\n" +
            "           sd4.dic_value renovationConditionValue,sd4.dic_name renovationConditionName,\n" +
            "           sd5.dic_value houseOrientationTypeValue,sd5.dic_name houseOrientationTypeName,\n" +
            "           sd6.dic_value payWayTypeValue,sd6.dic_name payWayTypeName,\n" +
            "           sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "           sd8.dic_value bedroomTypeValue,sd8.dic_name bedroomTypeName,\n" +
            "           sd9.dic_value bedroomFacingTypeValue,sd9.dic_name bedroomFacingTypeName,\n" +
            "           sd10.dic_value floorHeightTypeValue,sd10.dic_name floorHeightTypeName,\n" +
            "           hcs.consumer_service_phone consumerServicePhone,hcs.consumer_service_qq consumerServiceQq,hcs.consumer_service_wechat consumerServiceWechat\n" +
            " from t_hp_info hi\n" +
            "left join t_hp_resources hres on hi.id = hres.hp_info_id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_district sds on hl.district = sds.id\n" +
            "left join sys_city sc on sds.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join t_hp_furniture hf on hi.id = hf.hp_info_id\n" +
            "left join t_hp_rent hr on hi.id = hr.hp_info_id\n" +
            "left join t_hp_requirements hrs on hi.id = hrs.hp_info_id\n" +
            "left join sys_dictionary sd on sd.dic_value = hi.viewing_time\n" +
            "left join sys_dictionary sd2 on sd2.dic_value = hi.checkin_time\n" +
            "left join sys_dictionary sd3 on sd3.dic_value = hi.lease_type\n" +
            "left join sys_dictionary sd4 on sd4.dic_value = hi.renovation_condition\n" +
            "left join sys_dictionary sd5 on sd5.dic_value = hi.house_orientation_type\n" +
            "left join sys_dictionary sd6 on sd6.dic_value = hi.pay_way_type\n" +
            "left join sys_dictionary sd7 on sd7.dic_value = hi.lease_term_type\n" +
            "left join sys_dictionary sd8 on sd8.dic_value = hi.bedroom_type\n" +
            "left join sys_dictionary sd9 on sd9.dic_value = hi.bedroom_facing_type\n" +
            "left join sys_dictionary sd10 on sd10.dic_value = hi.floor_height\n" +
            "left join t_hp_customer_service hcs on hi.id = hcs.hp_info_id\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppHpInfoResourceSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 小程序 根据userId获取单个房屋详情
     * @param wrapper
     * @return
     */
    @Select("select hfh.id hpFollowHouseId,hi.business_id businessId,hfh.attention_status attentionStatus,hah.appointment_status appointmentStatus,\n" +
            "hi.id,hi.hp_location_id hpLocationId,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime,\n" +
            "hi.checkin_time checkinTime,hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.house_description houseDescription,\n" +
            "hi.floor_height floorHeight,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "hl.community_name communityName,hl.building,hl.descrip,hl.address,hl.elevator,hl.parking_space parkingSpace,hl.longitude,hl.latitude,\n" +
            "hf.hp_info_id hpFurnitureId,hf.broadband,hf.wardrobe,hf.television,hf.washing_machine washingMachine,hf.heating,\n" +
            "hf.heater,hf.bed,hf.sofa,hf.air_conditioner airConditioner,hf.refrigerator,hf.balcony,hf.private_toilet privateToilet,hf.mattress,\n" +
            "hf.bedside_cupboard bedsideCupboard,hf.smart_lock smartLock,hf.tea_table teaTable,hf.dining_table diningTable,hf.computer_desk computerDesk,\n" +
            "hf.gas_stoves gasStoves,hf.range_hood rangeHood,\n" +
            "hr.hp_info_id hpRentId,hr.management_fee managementFee,\n" +
            "hr.gas_fee gasFee,hr.heating_fee heatingFee,hr.cable_bill cableBill,hr.parking_fee parkingFee,\n" +
            "hrs.hp_info_id hpRequirementsId,hrs.girls_only girlsOnly,hrs.no_pets noPets,hrs.normal_schedule normalSchedule,hrs.no_smoking noSmoking,\n" +
            "sd.dic_value viewingTimeValue,sd.dic_name viewingTimeName,\n" +
            "sd2.dic_value checkinTimeValue,sd2.dic_name checkinTimeName,\n" +
            "sd3.dic_value leaseTypeValue,sd3.dic_name leaseTypeName,\n" +
            "sd4.dic_value renovationConditionValue,sd4.dic_name renovationConditionName,\n" +
            "sd5.dic_value houseOrientationTypeValue,sd5.dic_name houseOrientationTypeName,\n" +
            "sd6.dic_value payWayTypeValue,sd6.dic_name payWayTypeName,\n" +
            "sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "sd8.dic_value bedroomTypeValue,sd8.dic_name bedroomTypeName,\n" +
            "sd9.dic_value bedroomFacingTypeValue,sd9.dic_name bedroomFacingTypeName,\n" +
            "sd10.dic_value floorHeightTypeValue,sd10.dic_name floorHeightTypeName\n" +
            " from   t_hp_info hi\n" +
            "left join t_hp_location hl on  hi.hp_location_id = hl.id\n" +
            "left join t_hp_furniture hf on hi.id = hf.hp_info_id\n" +
            "left join t_hp_rent hr on hi.id = hr.hp_info_id\n" +
            "left join t_hp_requirements hrs on hi.id = hrs.hp_info_id\n" +
            "left join t_hp_follow_house hfh on hi.id = hfh.hp_info_id\n" +
            "left join t_hp_appointment_house hah on hi.id = hah.hp_info_id\n" +
            "left join sys_dictionary sd on  hi.viewing_time = sd.dic_value\n" +
            "left join sys_dictionary sd2 on  hi.checkin_time = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on  hi.lease_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on  hi.renovation_condition = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on  hi.house_orientation_type = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on  hi.pay_way_type = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on  hi.lease_term_type = sd7.dic_value\n" +
            "left join sys_dictionary sd8 on  hi.bedroom_type = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on  hi.bedroom_facing_type = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on  hi.floor_height = sd10.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppHpInfoSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 小程序获取单个房屋详情
     * @param wrapper
     * @return
     */
    @Select("select hi.business_id businessId,\n" +
            "hi.id,hi.hp_location_id hpLocationId,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime,\n" +
            "hi.checkin_time checkinTime,hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.house_description houseDescription,\n" +
            "hi.floor_height floorHeight,hi.agency_fee agencyFee,hi.service_fee serviceFee,hi.water_free waterFree,hi.electricity_bill electricityBill,hi.network_fee networkFee,\n" +
            "hl.community_name communityName,hl.building,hl.descrip,hl.address,hl.elevator,hl.parking_space parkingSpace,hl.longitude,hl.latitude,\n" +
            "hf.hp_info_id hpFurnitureId,hf.broadband,hf.wardrobe,hf.television,hf.washing_machine washingMachine,hf.heating,\n" +
            "hf.heater,hf.bed,hf.sofa,hf.air_conditioner airConditioner,hf.refrigerator,hf.balcony,hf.private_toilet privateToilet,hf.mattress,\n" +
            "hf.bedside_cupboard bedsideCupboard,hf.smart_lock smartLock,hf.tea_table teaTable,hf.dining_table diningTable,hf.computer_desk computerDesk,\n" +
            "hf.gas_stoves gasStoves,hf.range_hood rangeHood,\n" +
            "hr.hp_info_id hpRentId,hr.management_fee managementFee,\n" +
            "hr.gas_fee gasFee,hr.heating_fee heatingFee,hr.cable_bill cableBill,hr.parking_fee parkingFee,\n" +
            "hrs.hp_info_id hpRequirementsId,hrs.girls_only girlsOnly,hrs.no_pets noPets,hrs.normal_schedule normalSchedule,hrs.no_smoking noSmoking,\n" +
            "sd.dic_value viewingTimeValue,sd.dic_name viewingTimeName,\n" +
            "sd2.dic_value checkinTimeValue,sd2.dic_name checkinTimeName,\n" +
            "sd3.dic_value leaseTypeValue,sd3.dic_name leaseTypeName,\n" +
            "sd4.dic_value renovationConditionValue,sd4.dic_name renovationConditionName,\n" +
            "sd5.dic_value houseOrientationTypeValue,sd5.dic_name houseOrientationTypeName,\n" +
            "sd6.dic_value payWayTypeValue,sd6.dic_name payWayTypeName,\n" +
            "sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "sd8.dic_value bedroomTypeValue,sd8.dic_name bedroomTypeName,\n" +
            "sd9.dic_value bedroomFacingTypeValue,sd9.dic_name bedroomFacingTypeName,\n" +
            "sd10.dic_value floorHeightTypeValue,sd10.dic_name floorHeightTypeName\n" +
            " from   t_hp_info hi\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join t_hp_furniture hf on hi.id = hf.hp_info_id\n" +
            "left join t_hp_rent hr on hi.id = hr.hp_info_id\n" +
            "left join t_hp_requirements hrs on hi.id = hrs.hp_info_id\n" +
            "left join sys_dictionary sd on  hi.viewing_time = sd.dic_value\n" +
            "left join sys_dictionary sd2 on  hi.checkin_time = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on  hi.lease_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on  hi.renovation_condition = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on  hi.house_orientation_type = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on  hi.pay_way_type = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on  hi.lease_term_type = sd7.dic_value\n" +
            "left join sys_dictionary sd8 on  hi.bedroom_type = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on  hi.bedroom_facing_type = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on  hi.floor_height = sd10.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppHpInfoIdSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select hfh.id,hfh.user_id userId,hfh.hp_info_id,hfh.attention_status attentionStatus from t_hp_follow_house hfh ${ew.customSqlSegment}")
    HpFollowHouse getAppHpResourceFollowStatusById(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 小程序查询首页列表展示
     * @param wrapper
     * @return
     */
    @Select("select hi.id,hi.hp_location_id hpLocationId,hl.community_name communityName,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime,\n" +
            "        hi.checkin_time checkinTime,hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "        hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "        hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.house_type houseType,\n" +
            "        hl.longitude,hl.latitude,\n" +
            "        hres.id hpResourcesId,hres.hp_info_id hpResourcesInfoId,hres.house_resources_status houseResourcesStatus,\n" +
            "        sds.district_name districtName,sds.id districtId,\n" +
            "       sd.dic_value viewingTimeValue,sd.dic_name viewingTimeName,\n" +
            "       sd2.dic_value checkinTimeValue,sd2.dic_name checkinTimeName,\n" +
            "       sd3.dic_value leaseTypeValue,sd3.dic_name leaseTypeName,\n" +
            "       sd4.dic_value renovationConditionValue,sd4.dic_name renovationConditionName,\n" +
            "       sd5.dic_value houseOrientationTypeValue,sd5.dic_name houseOrientationTypeName,\n" +
            "       sd6.dic_value payWayTypeValue,sd6.dic_name payWayTypeName,\n" +
            "       sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "       sd8.dic_value bedroomTypeValue,sd8.dic_name bedroomTypeName,\n" +
            "       sd9.dic_value bedroomFacingTypeValue,sd9.dic_name bedroomFacingTypeName,\n" +
            "       sd10.dic_value floorHeightTypeValue,sd10.dic_name floorHeightTypeName,\n" +
            "       sd11.dic_value houseTypeValue,sd11.dic_name houseTypeName,\n" +
            "       ROUND(6378.138 * 2 * ASIN \n" +
            "       (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - hl.latitude * PI() / 180) / 2),2) + \n" +
            "       COS(#{userLatitude} * PI() / 180) * COS(hl.latitude * PI() / 180) * \n" +
            "       POW(SIN((#{userLongitude} * PI() / 180 - hl.longitude * PI() / 180) / 2),2))) * 1000) AS distance \n" +
            " from t_hp_info hi\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join t_hp_resources hres on hi.id = hres.hp_info_id\n" +
            "left join sys_district sds   on  hl.district = sds.id\n" +
            "left join sys_dictionary sd  on  hi.viewing_time = sd.dic_value  \n" +
            "left join sys_dictionary sd2 on  hi.checkin_time = sd2.dic_value  \n" +
            "left join sys_dictionary sd3 on  hi.lease_type  = sd3.dic_value  \n" +
            "left join sys_dictionary sd4 on  hi.renovation_condition = sd4.dic_value  \n" +
            "left join sys_dictionary sd5 on  hi.house_orientation_type = sd5.dic_value  \n" +
            "left join sys_dictionary sd6 on  hi.pay_way_type = sd6.dic_value  \n" +
            "left join sys_dictionary sd7 on  hi.lease_term_type = sd7.dic_value  \n" +
            "left join sys_dictionary sd8 on  hi.bedroom_type = sd8.dic_value  \n" +
            "left join sys_dictionary sd9 on  hi.bedroom_facing_type = sd9.dic_value  \n" +
            "left join sys_dictionary sd10 on hi.floor_height = sd10.dic_value\n" +
            "left join sys_dictionary sd11 on hi.house_type = sd11.dic_value\n" +
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getAppHpResourceSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude);

    @Select("select hi.id,hi.attention_status attentionStatus from t_hp_info hi ${ew.customSqlSegment}")
    HpInfo getAppHpResourceAttentionById(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select hfh.id,hfh.user_id userId,hfh.hp_info_id,hfh.attention_status attentionStatus from t_hp_follow_house hfh ${ew.customSqlSegment}")
    HpFollowHouse getAppHpInfoFollowById(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 小程序我关注的房源信息展示
     * @param wrapper
     * @return
     */
    @Select("select hfh.attention_status attentionStatus,\n" +
            "       hi.id,hl.community_name communityName,hi.lease_type leaseType,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "       hi.toilet,hi.house_area houseArea,hi.house_orientation_type houseOrientationType,hi.rental,hi.carousel_url carouselUrl,hi.vr_pictures_url vr_picturesUrl,\n" +
            "       hres.hp_info_id hpResourcesId,hres.house_resources_status houseResourcesStatus,\n" +
            "       hl.longitude,hl.latitude,\n" +
            "       sd.dic_value leaseTypeValue,sd.dic_name leaseTypeName,\n" +
            "       sd1.dic_value houseOrientationTypeValue,sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_value bedroomTypeValue,sd2.dic_name bedroomTypeName,\n" +
            "       sd3.dic_value bedroomFacingTypeValue,sd3.dic_name bedroomFacingTypeName,\n" +
            "       ROUND(6378.138 * 2 * ASIN\n" +
            "       (SQRT(POW(SIN((#{userLatitude} * PI() / 180 - hl.latitude * PI() / 180) / 2),2) +\n" +
            "       COS(#{userLatitude} * PI() / 180) * COS(hl.latitude * PI() / 180) *\n" +
            "       POW(SIN((#{userLongitude} * PI() / 180 - hl.longitude * PI() / 180) / 2),2))) * 1000) AS distance\n" +
            "from t_hp_follow_house hfh\n" +
            "left join t_hp_info hi on hfh.hp_info_id = hi.id\n" +
            "left join t_hp_resources hres on hi.id = hres.hp_info_id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_dictionary sd on hi.lease_type = sd.dic_value\n" +
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.bedroom_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.bedroom_facing_type = sd3.dic_value"+
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getAppHpPersonalCentreListPage(@Param(Constants.WRAPPER) Wrapper wrapper,@Param("userLongitude") Double userLongitude,@Param("userLatitude") Double userLatitude);

    /**
     * 小程序看房记录列表展示
     * @param wrapper
     * @return
     */
    @Select("select hah.id hpAppointmentHouseId,\n" +
            "       hcvr.id hpCustomerViewingRecordId,hcvr.view_house_time viewHouseTime,hcvr.viewing_status viewingStatus,hcvr.payment_status paymentStatus,\n" +
            "       hi.id,hi.business_id businessId,hi.lease_type leaseType,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "       hi.toilet,hi.house_area houseArea,hi.house_orientation_type houseOrientationType,hi.rental,hi.carousel_url carouselUrl,hi.vr_pictures_url vr_picturesUrl,\n" +
            "       hl.community_name communityName,hl.longitude,hl.latitude,\n" +
            "       sd.dic_value leaseTypeValue,sd.dic_name leaseTypeName,\n" +
            "       sd1.dic_value houseOrientationTypeValue,sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_value bedroomTypeValue,sd2.dic_name bedroomTypeName,\n" +
            "       sd3.dic_value bedroomFacingTypeValue,sd3.dic_name bedroomFacingTypeName\n" +
            "from t_hp_customer_viewing_record hcvr\n" +
            "left join t_hp_info hi on hcvr.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join t_hp_appointment_house hah on hcvr.hp_info_id = hah.hp_info_id\n" +
            "left join sys_dictionary sd on hi.lease_type = sd.dic_value\n" +
            "left join sys_dictionary sd1 on hi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.bedroom_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.bedroom_facing_type = sd3.dic_value\n" +
            " ${ew.customSqlSegment}")
    List<HpInfoVo> getAppHpCustomerViewingRecordSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 小程序看房记录单个房屋详情
     * 获取客户看房记录的infoid，查询
     * @param wrapper
     * @return
     */
    @Select("select\n" +
            "       hfh.attention_status attentionStatus,hah.id hpAppointmentHouseId,hah.appointment_status appointmentStatus,\n" +
            "       hcvr.id viewHouseCustomerViewingRecordId,hcvr.view_house_time viewingTime,hcvr.viewing_status viewingStatus,\n" +
            "       hi.id,hi.hp_location_id hpLocationId,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime,\n" +
            "       hi.checkin_time checkinTime,hi.carousel_url carouselUrl,hi.vr_pictures_url vrPicturesUrl,hi.video_url videoUrl,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "       hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "       hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.house_description houseDescription,\n" +
            "       hi.attention_status attentionStatus,hi.floor_height floorHeight,hi.agency_fee agencyFee,hi.service_fee serviceFee,\n" +
            "       hl.community_name communityName,hl.building,hl.descrip,hl.address,hl.elevator,hl.parking_space parkingSpace,hl.longitude,hl.latitude,\n" +
            "       hf.hp_info_id hpFurnitureId,hf.broadband,hf.wardrobe,hf.television,hf.washing_machine washingMachine,hf.heating,\n" +
            "       hf.heater,hf.bed,hf.sofa,hf.air_conditioner airConditioner,hf.refrigerator,hf.balcony,hf.private_toilet privateToilet,hf.mattress,\n" +
            "       hf.bedside_cupboard bedsideCupboard,hf.smart_lock smartLock,hf.tea_table teaTable,hf.dining_table diningTable,hf.computer_desk computerDesk,\n" +
            "       hf.gas_stoves gasStoves,hf.range_hood rangeHood,\n" +
            "       hr.hp_info_id hpRentId,hr.management_fee managementFee,\n" +
            "       hr.gas_fee gasFee,hr.heating_fee heatingFee,hr.cable_bill cableBill,hr.parking_fee parkingFee,\n" +
            "       hrs.hp_info_id hpRequirementsId,hrs.girls_only girlsOnly,hrs.no_pets noPets,hrs.normal_schedule normalSchedule,hrs.no_smoking noSmoking,\n" +
            "       sd.dic_value viewingTimeValue,sd.dic_name viewingTimeName,\n" +
            "       sd2.dic_value checkinTimeValue,sd2.dic_name checkinTimeName,\n" +
            "       sd3.dic_value leaseTypeValue,sd3.dic_name leaseTypeName,\n" +
            "       sd4.dic_value renovationConditionValue,sd4.dic_name renovationConditionName,\n" +
            "       sd5.dic_value houseOrientationTypeValue,sd5.dic_name houseOrientationTypeName,\n" +
            "       sd6.dic_value payWayTypeValue,sd6.dic_name payWayTypeName,\n" +
            "       sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "       sd8.dic_value bedroomTypeValue,sd8.dic_name bedroomTypeName,\n" +
            "       sd9.dic_value bedroomFacingTypeValue,sd9.dic_name bedroomFacingTypeName,\n" +
            "       sd10.dic_value floorHeightTypeValue,sd10.dic_name floorHeightTypeName,\n" +
            "       sd11.dic_value houseTypeValue,sd11.dic_name houseTypeName\n" +
            "from  t_hp_info hi\n" +
            "left join t_hp_customer_viewing_record hcvr on hcvr.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join t_hp_furniture hf on hi.id = hf.hp_info_id\n" +
            "left join t_hp_rent hr on hi.id = hr.hp_info_id\n" +
            "left join t_hp_requirements hrs on hi.id = hrs.hp_info_id\n" +
            "left join t_hp_follow_house hfh on hi.id = hfh.hp_info_id\n" +
            "left join t_hp_appointment_house hah on hi.id = hah.hp_info_id\n" +
            "left join sys_dictionary sd on hi.viewing_time = sd.dic_value\n" +
            "left join sys_dictionary sd2 on  hi.checkin_time = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on  hi.lease_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on  hi.renovation_condition = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on  hi.house_orientation_type = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on  hi.pay_way_type = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on  hi.lease_term_type = sd7.dic_value\n" +
            "left join sys_dictionary sd8 on  hi.bedroom_type = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on  hi.bedroom_facing_type = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on hi.floor_height = sd10.dic_value = \n" +
            "left join sys_dictionary sd11 on hi.house_type = sd11.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppHpCustomerViewingRecordById(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 小程序看房记录单个房屋详情
     * 获取客户看房记录的infoid，查询
     * @param wrapper
     * @return
     */
    @Select("select\n" +
            "       hcvr.id viewHouseCustomerViewingRecordId,hcvr.view_house_time viewingTime,hcvr.viewing_status viewingStatus,\n" +
            "       hi.id,hi.hp_location_id hpLocationId,hi.house_number houseNumber,hi.lease_type leaseType,hi.viewing_time viewingTime,\n" +
            "       hi.checkin_time checkinTime,hi.house_description houseDescription,hi.bedroom,hi.drawing_room drawingRoom,\n" +
            "       hi.toilet,hi.floor,hi.total_floor totalFloor,hi.house_area houseArea,hi.renovation_condition renovationCondition,hi.house_orientation_type houseOrientationType,hi.pay_way_type payWayType,\n" +
            "       hi.lease_term_type leaseTermType,hi.rental,hi.bedroom_type bedroomType,hi.bedroom_facing_type bedroomFacingType,hi.square_meter squareMeter,hi.house_description houseDescription,\n" +
            "       hi.attention_status attentionStatus,hi.floor_height floorHeight,hi.agency_fee agencyFee,hi.service_fee serviceFee,\n" +
            "       hl.community_name communityName,hl.building,hl.descrip,hl.address,hl.elevator,hl.parking_space parkingSpace,hl.longitude,hl.latitude,\n" +
            "       sds.district_name districtName,sc.city_name cityName, sp.province_name provinceName ,\n" +
            "       sd.dic_value viewingTimeValue,sd.dic_name viewingTimeName,\n" +
            "       sd2.dic_value checkinTimeValue,sd2.dic_name checkinTimeName,\n" +
            "       sd3.dic_value leaseTypeValue,sd3.dic_name leaseTypeName,\n" +
            "       sd4.dic_value renovationConditionValue,sd4.dic_name renovationConditionName,\n" +
            "       sd5.dic_value houseOrientationTypeValue,sd5.dic_name houseOrientationTypeName,\n" +
            "       sd6.dic_value payWayTypeValue,sd6.dic_name payWayTypeName,\n" +
            "       sd7.dic_value leaseTermTypeValue,sd7.dic_name leaseTermTypeName,\n" +
            "       sd8.dic_value bedroomTypeValue,sd8.dic_name bedroomTypeName,\n" +
            "       sd9.dic_value bedroomFacingTypeValue,sd9.dic_name bedroomFacingTypeName,\n" +
            "       sd10.dic_value floorHeightTypeValue,sd10.dic_name floorHeightTypeName,\n" +
            "       sd11.dic_value houseTypeValue,sd11.dic_name houseTypeName,\n" +
            "       hcs.consumer_service_phone consumerServicePhone,hcs.consumer_service_qq consumerServiceQq,hcs.consumer_service_wechat consumerServiceWechat\n" +
            "from t_hp_customer_viewing_record hcvr\n" +
            "left join t_hp_info hi on hcvr.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_district sds on hl.district = sds.id\n" +
            "left join sys_city sc on sds.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd on hi.viewing_time = sd.dic_value\n" +
            "left join sys_dictionary sd2 on  hi.checkin_time = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on  hi.lease_type = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on  hi.renovation_condition = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on  hi.house_orientation_type = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on  hi.pay_way_type = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on  hi.lease_term_type = sd7.dic_value\n" +
            "left join sys_dictionary sd8 on  hi.bedroom_type = sd8.dic_value\n" +
            "left join sys_dictionary sd9 on  hi.bedroom_facing_type = sd9.dic_value\n" +
            "left join sys_dictionary sd10 on hi.floor_height = sd10.dic_value = \n" +
            "left join t_hp_customer_service hcs on hi.id = hcs.hp_info_id\n" +
            "left join sys_dictionary sd11 on sd11.dic_value = hi.house_type\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getAppHpOrderById(@Param(Constants.WRAPPER) Wrapper wrapper);



    @Select("select hi.id,hi.rental from t_hp_info hi ${ew.customSqlSegment}")
    HpInfo getAppHpInfoOrderSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

}

