package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpOrder;
import com.jiyeyihe.cre.webapp.entity.ObOrder;
import com.jiyeyihe.cre.webapp.entity.ObOrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ObOrderMapper extends BaseMapper<ObOrder> {


    @Select("select oo.id,oo.order_no orderNo,oo.ob_info_id obInfoId,oo.total_fee totalFee,oo.order_status orderStatus,\n" +
            "       oi.id,hl.property_name propertyName,oi.rental_day rentalDay,oi.rental_month  rentalMonth,\n" +
            "       oi.construction_area constructionArea,oi.office_building_type officeBuildingType,\n" +
            "       oi.house_orientation_type houseOrientationType,oi.carousel_url carouselUrl,oi.vr_pictures_url vrPicturesUrl,\n" +
            "       hres.ob_info_id obHouseResourcesId,hres.office_resources_status officeResourcesStatus,\n" +
            "       ocvr.id obCustomerViewingRecordId,\n" +
            "       sd.district_name districtName,sc.city_name cityName, sp.province_name provinceName,\n" +
            "       sd1.dic_value houseOrientationTypeValue,sd1.dic_name houseOrientationTypeName,\n" +
            "       sd2.dic_value leaseTypeValue,sd2.dic_name leaseTypeName,\n" +
            "       sd3.dic_value officeBuildingTypeValue,sd3.dic_name officeBuildingTypeName,\n" +
            "       sd4.dic_value natureTypeValue,sd4.dic_name natureTypeName,\n" +
            "       sd5.dic_value floorHeightTypeValue,sd5.dic_name floorHeightTypeName,\n" +
            "       sd6.dic_value registeredTypeValue,sd6.dic_name registeredTypeName,\n" +
            "       sd7.dic_value segmentationTypeValue,sd7.dic_name segmentationTypeName\n" +
            "from t_ob_order oo\n" +
            "left join t_ob_info oi on oo.ob_info_id = oi.id\n" +
            "left join t_ob_resources hres on oi.id = hres.ob_info_id\n" +
            "left join t_ob_location hl on oi.ob_location_id = hl.id\n" +
            "left join t_ob_customer_viewing_record ocvr on ocvr.ob_info_id = oi.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd1 on oi.house_orientation_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on oi.lease_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on oi.office_building_type = sd3.dic_value = \n" +
            "left join sys_dictionary sd4 on oi.nature = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on oi.floor_height = sd5.dic_value\n" +
            "left join sys_dictionary sd6 on oi.registered = sd6.dic_value\n" +
            "left join sys_dictionary sd7 on oi.segmentation = sd7.dic_value\n" +
            " ${ew.customSqlSegment}")
    List<ObOrderVo> getAppObOrderSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);




    @Select("select oo.total_fee totalFee,oi.rental_month rentalMonth,oi.rental_day rentalDay,oi.agency_fee agencyFee,oi.service_fee serviceFee,oi.createtime,\n" +
            "       hl.property_name propertyName," +
            "       sd1.dic_value officeBuildingTypeValue,sd1.dic_name officeBuildingTypeName,\n" +
            "       sd2.dic_value natureTypeValue,sd2.dic_name natureTypeName,\n" +
            "       sd3.dic_value renovationConditionTypeValue,sd3.dic_name renovationConditionTypeName,\n" +
            "       sd4.dic_value registeredTypeValue,sd4.dic_name registeredTypeName,\n" +
            "       sd5.dic_value segmentationTypeValue,sd5.dic_name segmentationTypeName,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_ob_order oo\n" +
            "left join t_ob_info oi on oo.ob_info_id = oi.id\n" +
            "left join t_ob_location hl on hl.id = oi.ob_location_id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd1 on oi.office_building_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on oi.nature = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on oi.renovation_condition = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on oi.registered = sd4.dic_value\n" +
            "left join sys_dictionary sd5 on oi.segmentation = sd5.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getIdAppObOrderDetail(@Param(Constants.WRAPPER) Wrapper wrapper);

}
