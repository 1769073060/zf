package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.ObCustomerViewingRecord;
import com.jiyeyihe.cre.webapp.entity.ObCustomerViewingRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ObCustomerViewingRecordMapper extends BaseMapper<ObCustomerViewingRecord> {

    /**
     * 客户看房记录分页查询
     * @param wrapper
     * @return
     */
    @Select("select hcvr.id obCustomerViewingRecordId,hcvr.customer_name customerName,hcvr.viewing_status viewingStatus,hi.rental_day rentalDay,hi.rental_month rentalMonth,\n" +
            "       hi.id obInfoId,hi.house_number houseNumber,\n" +
            "       hl.id,\n" +
            "       hi.property_name  propertyName,hi.house_number houseNumber,hi.lease_type leaseType,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            " from t_ob_customer_viewing_record hcvr\n" +
            "left join t_ob_info hi on hcvr.ob_info_id = hi.id\n" +
            "left join t_ob_resources hr on hr.ob_info_id = hi.id\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id"+
            " ${ew.customSqlSegment}")
    List<ObCustomerViewingRecordVo> getObCustomerViewingRecordSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 查询单个客戶看房记录
     * @param wrapper
     * @return
     */
    @Select("select hcvr.id,hcvr.customer_name customerName,hcvr.viewing_status viewingStatus,hi.rental_day rentalDay,hi.rental_month rentalMonth,\n" +
            "       hi.id obInfoId,hi.house_number houseNumber,\n" +
            "       hi.property_name  propertyName,hi.house_number houseNumber,hi.lease_type leaseType,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            " from t_ob_customer_viewing_record hcvr\n" +
            "left join t_ob_info hi on hcvr.ob_info_id = hi.id\n" +
            "left join t_ob_resources hr on hr.ob_info_id = hi.id\n" +
            "left join t_ob_location hl on hi.ob_location_id = hl.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id"+
            " ${ew.customSqlSegment}")
    Map<String,Object> getObCustomerViewingRecordSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select hcvr.id,hcvr.customer_name customerName,hcvr.viewing_status viewingStatus from t_ob_customer_viewing_record hcvr ${ew.customSqlSegment}")
    ObCustomerViewingRecord getByIdAppObCustomerViewingRecord(@Param(Constants.WRAPPER) Wrapper wrapper);

}
