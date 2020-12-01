package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpOrder;
import com.jiyeyihe.cre.webapp.entity.HpOrderVo;
import com.jiyeyihe.cre.webapp.entity.ObInfoVo;
import com.jiyeyihe.cre.webapp.entity.ObOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HpOrderMapper extends BaseMapper<HpOrder> {

    @Select("select ho.id,ho.order_no orderNo,ho.hp_info_id hpInfoId,ho.total_fee totalFee,ho.order_status orderStatus,hl.community_name communityName,\n" +
            "       hi.rental,hi.carousel_url carouselUrl,hi.house_area houseArea,hi.bedroom,hi.drawing_room drawingRoom,hi.toilet,\n" +
            "       hcvr.id hpCustomerViewingRecordId,\n" +
            "       sd1.dic_value leaseTypeValue,sd1.dic_name leaseTypeName,\n" +
            "       sd2.dic_value houseTypeValue,sd2.dic_name houseTypeName,\n" +
            "       sd3.dic_value renovationTypeValue,sd3.dic_name renovationTypeName,\n" +
            "       sd4.dic_value houseOrientationTypeValue,sd4.dic_name houseOrientationTypeName," +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_order ho\n" +
            "left join t_hp_info hi on ho.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hl.id = hi.hp_location_id\n" +
            "left join t_hp_customer_viewing_record hcvr on hcvr.hp_info_id = hi.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd1 on hi.lease_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.house_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.renovation_condition = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on hi.house_orientation_type = sd4.dic_value" +
            " ${ew.customSqlSegment}")
    List<HpOrderVo> getAppHpOrderSelectListPage(@Param(Constants.WRAPPER) Wrapper wrapper);


    @Select("select ho.total_fee totalFee,ho.order_status orderStatus,hl.community_name communityName,ho.createtime,\n" +
            "       hi.rental,hi.agency_fee agencyFee,hi.service_fee serviceFee,\n" +
            "       sd1.dic_value leaseTypeValue,sd1.dic_name leaseTypeName,\n" +
            "       sd2.dic_value houseTypeValue,sd2.dic_name houseTypeName,\n" +
            "       sd3.dic_value renovationTypeValue,sd3.dic_name renovationTypeName,\n" +
            "       sd4.dic_value payWayTypeValue,sd4.dic_name payWayTypeName,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            "from t_hp_order ho\n" +
            "left join t_hp_info hi on ho.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id\n" +
            "left join sys_dictionary sd1 on hi.lease_type = sd1.dic_value\n" +
            "left join sys_dictionary sd2 on hi.house_type = sd2.dic_value\n" +
            "left join sys_dictionary sd3 on hi.renovation_condition = sd3.dic_value\n" +
            "left join sys_dictionary sd4 on hi.pay_way_type = sd4.dic_value\n" +
            "${ew.customSqlSegment}")
    Map<String,Object> getIdAppHpOrderDetail(@Param(Constants.WRAPPER) Wrapper wrapper);
}
