package com.jiyeyihe.cre.webapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@TableName("t_hp_info")
public class HpInfoVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long hpLocationId;
    private String communityName;
    private String houseNumber;
    private Integer leaseType;
    private Integer viewingTime;
    private Integer checkinTime;
    private String carouselUrl;
    private String vrPicturesUrl;
    private String videoUrl;
    private String houseDescription;
    private Integer bedroom;
    private Integer drawingRoom;
    private Integer toilet;
    private Integer houseType;
    private Integer floor;
    private Integer totalFloor;
    private Double houseArea;
    private Integer renovationCondition;
    private Integer houseOrientationType;
    private Integer payWayType;
    private Integer leaseTermType;
    private Double rental;
    private Integer bedroomType;
    private Integer bedroomFacingType;
    private Double squareMeter;
    private Integer attentionStatus;
    private Double agencyFee;
    private Double serviceFee;
	private Double waterFree;
    private Double electricityBill;
    private Double networkFee;
    private Long businessId;
    private Long createtime;
    private Long updatetime;

    /*房屋出租要求*/
    private Long hpRequirementsId;
    private Integer girlsOnly;
    private Integer noPets;
    private Integer normalSchedule;
    private Integer noSmoking;
    /*房屋租金*/
    private Long hpRentId;
    private Integer managementFee;
    private Integer gasFee;
    private Integer heatingFee;
    private Integer cableBill;
    private Integer parkingFee;
    /*家具*/
    private Long hpFurnitureId;
    private Integer broadband;
    private Integer wardrobe;
    private Integer television;
    private Integer washingMachine;
    private Integer heating;
    private Integer heater;
    private Integer bed;
    private Integer sofa;
    private Integer airConditioner;
    private Integer refrigerator;
    private Integer balcony;
    private Integer privateToilet;
    private Integer mattress;
    private Integer bedsideCupboard;
    private Integer smartLock;
    private Integer teaTable;
    private Integer diningTable;
    private Integer computerDesk;
    private Integer gasStoves;
    private Integer rangeHood;

    private Integer houseResourcesStatus;
    private Long hpInfoId;
    private String descrip;

    private Integer leaseTypeValue;
    private String leaseTypeName;
    private Integer houseOrientationTypeValue;
    private String houseOrientationTypeName;

    private Integer floorHeightTypeValue;
    private String floorHeightTypeName;
    private Integer viewingTimeValue;
    private String viewingTimeName;
    private Integer checkinTimeValue;
    private String checkinTimeName;
    private Integer renovationConditionValue;
    private String renovationConditionName;
    private Integer payWayTypeValue;
    private String payWayTypeName;
    private Integer leaseTermTypeValue;
    private String leaseTermTypeName;
    private Integer bedroomTypeValue;
    private String  bedroomTypeName;
    private Integer bedroomFacingTypeValue;
    private String  bedroomFacingTypeName;
    private String districtName;
    private String cityName;
    private String provinceName;
    private Long hpResourcesId;
    private Long hpResourcesInfoId;
    private Double longitude;
    private Double latitude;
    private Long hpCustomerViewingRecord;
    private Long hpCustomerViewingRecordId;
    private Double distance;
    private Integer paymentStatus;
    private Integer viewingStatus;
    private Integer quantityStatus;
    private Long districtId;
    private Long cityId;
    private Long provinceId;
    private Long hpAppointmentHouseId;


}
