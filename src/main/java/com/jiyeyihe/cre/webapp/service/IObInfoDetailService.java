package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.*;

public interface IObInfoDetailService extends IService<ObInfo> {

    /**
     * 写字楼列表显示
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getObInfoDetailListPage(Long pageNum, Long pageSize, String propertyName, Long businessId) throws Exception;

    /**
     * 添加写字楼信息
     * @param obInfo
     * @return
     * @throws Exception
     */
    public boolean addObInfoDetailInfo(ObInfo obInfo) throws Exception;

    /**
     * 查询写字楼 最高id
     * @return
     */
    public ObInfo listMaxId();

    /**
     * 添加写字楼详细信息
     * @param obMatching
     * @param obRent
     * @param obSurroundingFacilities
     * @return
     * @throws Exception
     */
    public String addObInfoDetail( ObMatching obMatching, ObRent obRent,ObSurroundingFacilities obSurroundingFacilities) throws Exception;

    /**
     * 查询单个写字楼详细信息
     * @param id
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getObInfoDetailSelectById(Long id,Long businessId) throws Exception;

    /**
     * 获取写字楼楼盘初始化下拉框
     * @param businessId
     * @return
     * @throws Exception
     */
    public String getObInfoDetailListPropertyName(Long businessId) throws Exception;


    /**
     * app写字楼首页列表分页查询
     * @param pageNum
     * @param pageSize
     * @param cityName
     * @return
     * @throws Exception
     */
    public String getAppObInfoDetailListPage(Long pageNum, Long pageSize, String cityName,Double userLongitude,Double userLatitude) throws Exception;

    /**
     * 根据用户id查询关注表和写字楼信息id   进行查询单个房屋信息
     * @param id 房屋信息id
     * @param userId 用户Id
     * @return
     * @throws Exception
     */
    public String getAppObInfoSelectById(Long id,Long userId) throws Exception;

    /**
     * 根据用户id查询   进行查询单个房屋信息
     * @param id 房屋信息id
     * @return
     * @throws Exception
     */
    public String getAppObInfoIdSelectById(Long id) throws Exception;

    /**
     * 多条件筛选房屋
     * @param
     * @param
     * @return
     */
    public String getAppObResourceSelectListPage(Long pageNum, Long pageSize,
                                                      String propertyName, String provinceName, String cityName, String districtName,
                                                      Integer kilometer, Double longitude, Double latitude,
                                                      Integer minRent, Integer maxRent,
                                                      Integer leaseTypeValue,
                                                      Integer pureOffice,Integer commercialComplex,Integer businessApartment,Integer businessHotel,Integer corporateOffice,Integer compoundOfficeBuilding,
                                                      Integer natureTypeValue,
                                                      Integer floorHeightTypeValue,Integer registeredTypeValue,
                                                      Integer segmentationTypeValue,
                                                      Double userLongitude, Double userLatitude)throws Exception;

    /**
     * app查询用户浏览这个房屋的信息 有没有存在这个关注表里面
     * @param id
     * @param userId
     * @return
     */
    public ObFollowHouse getAppObResourceFollowStatusById(Long id,Long userId);

    /**
     * 查询当前进入写字楼id
     * @param id
     * @return
     */
    public ObInfo getAppObResourceAttentionById(Long id);


    /**
     * app查询单个写字楼信息id
     * @param id
     * @param userId
     * @return
     */
    public ObFollowHouse getAppObInfoFollowById(Long id,Long userId);



    /**
     * app我关注的房源列表展示
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     * @throws Exception
     */
    public String getAppObPersonalCentreListPage(Long pageNum, Long pageSize,Long userId,Double userLongitude, Double userLatitude) throws Exception;



    /**
     * 获取用户经纬度和房屋经纬度查询公里
     * @param userLongitude    用户经度
     * @param userLatitude     用户纬度
     * @param houseLongitude   房子经度
     * @param houseLatitude    房子纬度
     * @return
     * @throws Exception
     */
    public String getIdLongitudeLatitude(Double userLongitude,Double userLatitude,Double houseLongitude,Double houseLatitude) throws Exception;


    /**
     * 查询单个写字楼信息
     * @param id
     * @return
     */
    public ObInfo getAppObInfoOrderSelectById(Long id);
}
