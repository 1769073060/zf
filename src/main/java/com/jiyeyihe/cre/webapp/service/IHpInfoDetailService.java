package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface IHpInfoDetailService extends IService<HpInfo> {

    /**
     * 房屋信息分页查询
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getHpInfoDetailListPage(Long pageNum, Long pageSize, String communityName, Long businessId) throws Exception;

    /**
     * 获取房屋详情小区初始化下拉框
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getHpInfoDetailListCommunityName(Long businessId) throws Exception;

    /**
     * 添加房屋信息
     * @param hpInfo
     * @return
     * @throws Exception
     */
    public boolean addHpInfoDetailInfo(HpInfo hpInfo) throws Exception;

    /**
     * 添加房屋信息
     * @param hpFurniture
     * @param hpRent
     * @param hpRequirements
     * @return
     * @throws Exception
     */
    public String addHpInfoDetail(HpFurniture hpFurniture, HpRent hpRent, HpRequirements hpRequirements) throws Exception;

    public HpInfo listMaxId();

    /**
     * 查询单个房屋信息
     * @param id
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getHpInfoDetailSelectById(Long id,Long businessId) throws Exception;

    /**
     * app房屋信息分页查询
     * @param pageNum
     * @param pageSize
     * @param cityName
     * @return
     * @throws Exception
     */
    public String getAppHpInfoDetailListPage(Long pageNum, Long pageSize, String cityName,Double userLongitude,Double userLatitude) throws Exception;

    /**
     * 查询单个我发布房源信息
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppHpInfoDetailSelectById(Long id,Long userId) throws Exception;

    /**
     * app查询用户浏览这个房屋的信息 有没有存在这个关注表里面
     * @param id
     * @param userId
     * @return
     */
    public HpFollowHouse getAppHpResourceFollowStatusById(Long id,Long userId);

    /**
     * 根据用户id查询关注表和房屋信息id   进行查询单个房屋信息
     * @param id 房屋信息id
     * @param userId 用户Id
     * @return
     * @throws Exception
     */
    public String getAppHpInfoSelectById(Long id,Long userId) throws Exception;

    /**
     * 根据用户id查询 进行查询单个房屋信息
     * @param id 房屋信息id
     * @return
     * @throws Exception
     */
    public String getAppHpInfoIdSelectById(Long id) throws Exception;

    /**
     * app 多条件筛选房屋
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @param provinceName
     * @param cityName
     * @param districtName
     * @param leaseTypeValue
     * @param minRent
     * @param maxRent
     * @param floorHeightTypeValue
     * @param viewingTimeValue
     * @param renovationConditionValue
     * @param payWayTypeValue
     * @param houseOrientationTypeValue
     * @param houseTypeValue
     * @param television
     * @param washingMachine
     * @param airConditioner
     * @param kilometer
     * @param longitude
     * @param latitude
     * @return
     * @throws Exception
     */
    public String getAppHpResourceSelectListPage(Long pageNum, Long pageSize, String communityName, String provinceName, String cityName, String districtName, Integer leaseTypeValue, String minRent, String maxRent, Integer floorHeightTypeValue, Integer viewingTimeValue, Integer renovationConditionValue, Integer payWayTypeValue, Integer houseOrientationTypeValue, Integer houseTypeValue, Integer television, Integer washingMachine, Integer airConditioner, Integer kilometer, Double longitude, Double latitude,Double userLongitude,Double userLatitude) throws Exception;

    /**
     * 查询单个房屋信息
     * @param id
     * @return
     */
    public HpInfo getAppHpResourceAttentionById(Long id);

    /**
     * app查询当前userId和房屋id有没有在关注表里面
     * @param id
     * @param userId
     * @return
     */
    public HpFollowHouse getAppHpInfoFollowById(Long id,Long userId);

    /**
     * app我关注的房源列表展示
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppHpPersonalCentreListPage(Long pageNum, Long pageSize,Long userId,Double userLongitude, Double userLatitude) throws Exception;


    /**
     * 查询单个房屋信息
     * @param id
     * @return
     */
    public HpInfo getAppHpInfoOrderSelectById(Long id);
}