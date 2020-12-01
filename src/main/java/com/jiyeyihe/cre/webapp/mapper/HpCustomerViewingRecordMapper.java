package com.jiyeyihe.cre.webapp.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jiyeyihe.cre.webapp.entity.HpCustomerViewingRecord;
import com.jiyeyihe.cre.webapp.entity.HpCustomerViewingRecordVo;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.entity.HpResources;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HpCustomerViewingRecordMapper extends BaseMapper<HpCustomerViewingRecord> {

    /**
     * 客户看房记录分页查询
     * @param wrapper
     * @return
     */
    @Select("select hcvr.id hpCustomerViewingRecordId,hcvr.customer_name customerName,hcvr.viewing_status viewingStatus,\n" +
            "       hi.id hpHouseInfoId,hi.community_name  communityName,hi.house_number houseNumber,\n" +
            "       hi.lease_type leaseType,hi.bedroom,hi.drawing_room drawingRoom,hi.toilet,hi.rental,\n" +
            "       hl.id,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            " from t_hp_customer_viewing_record hcvr\n" +
            "left join t_hp_info hi on hcvr.hp_info_id = hi.id\n" +
            "left join t_hp_resources hr on hr.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id"+
            " ${ew.customSqlSegment}")
    List<HpCustomerViewingRecordVo> getHpCustomerViewingRecordSelectList(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 查询单个客戶看房记录
     * @param wrapper
     * @return
     */
    @Select("select hcvr.id,hcvr.customer_name customerName,hcvr.viewing_status viewingStatus,\n" +
            "       hi.id hpHouseInfoId,hi.community_name  communityName,hi.house_number houseNumber,\n" +
            "       hi.lease_type leaseType,hi.bedroom,hi.drawing_room drawingRoom,hi.toilet,hi.rental,\n" +
            "       hl.district,hl.community_name communityName,hl.address,hl.descrip, hl.longitude,hl.latitude,\n" +
            "       sd.district_name districtName,sc.city_name cityName,sp.province_name provinceName\n" +
            " from t_hp_customer_viewing_record hcvr\n" +
            "left join t_hp_info hi on hcvr.hp_info_id = hi.id\n" +
            "left join t_hp_resources hr on hr.hp_info_id = hi.id\n" +
            "left join t_hp_location hl on hi.hp_location_id = hl.id\n" +
            "left join sys_district sd on hl.district = sd.id\n" +
            "left join sys_city sc on sd.city_id = sc.id\n" +
            "left join sys_province sp on sc.province_id = sp.id"+
            " ${ew.customSqlSegment}")
    Map<String,Object> getHpCustomerViewingRecordSelectById(@Param(Constants.WRAPPER) Wrapper wrapper);



    @Select("select hcvr.id,hcvr.customer_name customerName,hcvr.viewing_status viewingStatus from t_hp_customer_viewing_record hcvr ${ew.customSqlSegment}")
    HpCustomerViewingRecord getByIdAppHpCustomerViewingRecord(@Param(Constants.WRAPPER) Wrapper wrapper);

}
