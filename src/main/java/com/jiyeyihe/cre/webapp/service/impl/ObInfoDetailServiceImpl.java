package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.mapper.ObInfoMapper;
import com.jiyeyihe.cre.webapp.service.IObInfoDetailService;
import com.jiyeyihe.cre.webapp.service.IObMatchingService;
import com.jiyeyihe.cre.webapp.service.IObRentService;
import com.jiyeyihe.cre.webapp.service.IObSurroundingFacilitiesService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Service
@Transactional
public class ObInfoDetailServiceImpl extends ServiceImpl<ObInfoMapper, ObInfo> implements IObInfoDetailService {


    @Resource
    private ObInfoMapper obInfoMapper;
    @Resource
    private IObInfoDetailService iObInfoDetailService;
    @Resource
    private IObSurroundingFacilitiesService iObSurroundingFacilitiesService;
    @Resource
    private IObMatchingService iObMatchingService;
    @Resource
    private IObRentService iObRentService;
    @Resource
    private FileUtils fileUtils;


    @Override
    public String getObInfoDetailListPage(Long pageNum, Long pageSize, String propertyName, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        if (propertyName != null || "".equals(propertyName)) {
            queryWrapper.like("hi.property_name", propertyName)
                    .or().like("hi.house_number", propertyName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        List<ObInfoVo> roleList = obInfoMapper.getObInfoDetailSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obInfoMapper.getObInfoDetailSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public boolean addObInfoDetailInfo(ObInfo obInfo) throws Exception {
        if (obInfo.getFloor() >= MsgConsts.ZERO_STATUS && obInfo.getFloor() <= MsgConsts.THIRD_STATUS) {
            obInfo.setFloorHeight(MsgConsts.FIRST_STATUS);
        }
        if (obInfo.getFloor() >= MsgConsts.FOURTH_STATUS && obInfo.getFloor() <= MsgConsts.SIXTH_STATUS) {
            obInfo.setFloorHeight(MsgConsts.SECOND_STATUS);
        }
        if (obInfo.getFloor() >= MsgConsts.SEVENTH_STATUS) {
            obInfo.setFloorHeight(MsgConsts.THIRD_STATUS);
        }

        boolean save = iObInfoDetailService.save(obInfo);
        if (save == true) {
            return true;
        }
        return false;
    }

    @Override
    public ObInfo listMaxId() {
        ObInfo selectMaxId = obInfoMapper.getObInfoDetailSelectMaxId();
        return selectMaxId;
    }

    @Override
    public String addObInfoDetail(ObMatching ObMatching, ObRent ObRent,ObSurroundingFacilities ObSurroundingFacilities) throws Exception {
        boolean matchingSave = iObMatchingService.save(ObMatching);
        boolean rentSave = iObRentService.save(ObRent);
        boolean surroundingFacilitiesSave = iObSurroundingFacilitiesService.save(ObSurroundingFacilities);
        if ( matchingSave == true && rentSave == true && surroundingFacilitiesSave == true ) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    @Override
    public String getObInfoDetailSelectById(Long id, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        ObInfo ObInfo = obInfoMapper.selectById(id);
        ObInfo.setCarouselUrl(fileUtils.getManyUrl(ObInfo.getCarouselUrl()));
        ObInfo.setVrPicturesUrl(fileUtils.getIpaURl(ObInfo.getVrPicturesUrl()));
        ObInfo.setVideoUrl(fileUtils.getIpaURl(ObInfo.getVideoUrl()));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hi.id", id);
        queryWrapper.eq("hi.business_id", businessId);
        Map<String, Object> selectById = obInfoMapper.getObInfoDetailSelectById(queryWrapper);
        selectById.put("carouselUrl", ObInfo.getCarouselUrl());
        selectById.put("vrPicturesUrl", ObInfo.getVrPicturesUrl());
        selectById.put("videoUrl", ObInfo.getVideoUrl());
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public String getObInfoDetailListPropertyName(Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hl.business_id",businessId);
        }
        queryWrapper.orderByDesc("id");
        List<ObLocation> roleList = obInfoMapper.getObInfoDetailListPropertyName(queryWrapper);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public String getAppObInfoDetailListPage(Long pageNum, Long pageSize, String cityName,Double userLongitude,Double userLatitude) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        if (cityName != null || "".equals(cityName)) {
            queryWrapper.like("sc.city_name", cityName);
        }
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 176).ge("sd2.id", 171);
        queryWrapper.le("sd3.id", 170).ge("sd3.id", 169);
        queryWrapper.eq("hres.office_resources_status", 1);// 根据状态查询
        List<ObInfoVo> roleList = obInfoMapper.getAppObInfoDetailSelectList(queryWrapper,userLongitude,userLatitude);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
//        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obInfoMapper.getAppObInfoDetailSelectList(queryWrapper,userLongitude,userLatitude);
        List<ObInfoVo> arrayList = new ArrayList<>();
        for (ObInfoVo obInfoVo : roleList) {
            if (!isEmpty(obInfoVo.getCarouselUrl())) {
                obInfoVo.setCarouselUrl(fileUtils.getManyUrl(obInfoVo.getCarouselUrl()));
                String carouselUrl = obInfoVo.getCarouselUrl();
                String url = obInfoVo.getCarouselUrl();
                int obUrl = url.length()-url.replace(",","").length();
                int i = (obUrl-2);
                //如果图片超过3张返回张数
                obInfoVo.setQuantityStatus(i);
                if (i>0){
                    int indexOf = carouselUrl.indexOf(",");
                    int indexOf2 = carouselUrl.indexOf(",",indexOf+1);
                    int indexOf3 = carouselUrl.indexOf(",",indexOf2+1);
                    String obInfoJpg = carouselUrl.substring(0, indexOf3);
                    obInfoVo.setCarouselUrl(obInfoJpg);
                }else {
                    obInfoVo.setCarouselUrl(carouselUrl);
                }

            }
            arrayList.add(obInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }

    /*获取房源信息*/
    @Override
    public String getAppObInfoSelectById(Long id,Long userId) throws Exception {
        JSONObject object = new JSONObject();
        ObInfo ObInfo = obInfoMapper.selectById(id);
        ObInfo.setCarouselUrl(fileUtils.getManyUrl(ObInfo.getCarouselUrl()));
        String videoUrl = ObInfo.getVideoUrl();
        String vrPicturesUrl = ObInfo.getVrPicturesUrl();
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 123).ge("sd1.id", 120);
        queryWrapper.le("sd2.id", 133).ge("sd2.id", 124);
        queryWrapper.le("sd3.id", 139).ge("sd3.id", 134);
        queryWrapper.le("sd4.id", 161).ge("sd4.id", 159);
        queryWrapper.le("sd5.id", 176).ge("sd5.id", 171);
        queryWrapper.le("sd6.id", 179).ge("sd6.id", 177);
        queryWrapper.le("sd8.id", 186).ge("sd8.id", 182);
        queryWrapper.le("sd9.id", 192).ge("sd9.id", 187);
        queryWrapper.le("sd10.id", 194).ge("sd10.id", 193);
        queryWrapper.le("sd11.id", 196).ge("sd11.id", 195);
        queryWrapper.eq("hi.id", id);
        queryWrapper.eq("hfh.ob_info_id", id);
        queryWrapper.eq("hfh.user_id",userId);
        queryWrapper.eq("hah.ob_info_id", id);
        queryWrapper.eq("hah.user_id",userId);
        Map<String, Object> selectById = obInfoMapper.getAppObInfoSelectById(queryWrapper);
        selectById.put("carouselUrl", ObInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            ObInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", ObInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            ObInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", ObInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public String getAppObInfoIdSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        ObInfo ObInfo = obInfoMapper.selectById(id);
        ObInfo.setCarouselUrl(fileUtils.getManyUrl(ObInfo.getCarouselUrl()));
        String videoUrl = ObInfo.getVideoUrl();
        String vrPicturesUrl = ObInfo.getVrPicturesUrl();
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 123).ge("sd1.id", 120);
        queryWrapper.le("sd2.id", 133).ge("sd2.id", 124);
        queryWrapper.le("sd3.id", 139).ge("sd3.id", 134);
        queryWrapper.le("sd4.id", 161).ge("sd4.id", 159);
        queryWrapper.le("sd5.id", 176).ge("sd5.id", 171);
        queryWrapper.le("sd6.id", 179).ge("sd6.id", 177);
        queryWrapper.le("sd7.id", 181).ge("sd7.id", 180);
        queryWrapper.le("sd8.id", 186).ge("sd8.id", 182);
        queryWrapper.le("sd9.id", 192).ge("sd9.id", 187);
        queryWrapper.le("sd10.id", 194).ge("sd10.id", 193);
        queryWrapper.le("sd11.id", 196).ge("sd11.id", 195);
        queryWrapper.eq("hi.id", id);
        Map<String, Object> selectById = obInfoMapper.getAppObInfoIdSelectById(queryWrapper);
        selectById.put("carouselUrl", ObInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            ObInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", ObInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            ObInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", ObInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public String getAppObResourceSelectListPage(Long pageNum, Long pageSize,
                                                      String propertyName, String provinceName, String cityName, String districtName,
                                                      Integer kilometer, Double longitude, Double latitude,
                                                      Integer minRent, Integer maxRent,
                                                      Integer leaseTypeValue,
                                                      Integer pureOffice, Integer commercialComplex, Integer businessApartment, Integer businessHotel, Integer corporateOffice, Integer compoundOfficeBuilding,
                                                      Integer natureTypeValue,
                                                      Integer floorHeightTypeValue, Integer registeredTypeValue,
                                                      Integer segmentationTypeValue,
                                                      Double userLongitude, Double userLatitude) throws Exception{
        JSONObject object = new JSONObject();
        IPage<ObInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        if (propertyName != null || "".equals(propertyName)) {
            queryWrapper.eq("hi.property_name", propertyName);
        }
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 170).ge("sd2.id", 169);
        queryWrapper.le("sd3.id", 176).ge("sd3.id", 171);
        queryWrapper.le("sd4.id", 181).ge("sd4.id", 180);
        queryWrapper.le("sd5.id", 161).ge("sd5.id", 159);
        queryWrapper.le("sd6.id", 194).ge("sd6.id", 193);
        queryWrapper.le("sd7.id", 196).ge("sd7.id", 195);

        queryWrapper.eq("hres.office_resources_status", 1);// 根据状态查询
        /*区域查询*/
        if (provinceName != null || "".equals(provinceName)) {
            queryWrapper.eq("sp.id", provinceName);
        }
        if (cityName != null || "".equals(cityName)) {
            queryWrapper.eq("sc.id", cityName);
        }
        if (districtName != null || "".equals(districtName)){
            queryWrapper.eq("sd.id", districtName);
        }
        /*区域经纬度*/
        if (longitude != null && latitude != null) {
            double r = MsgConsts.RADIUS;//地球半径千米
            double dis = 10;//10千米距离
            double dlng = MsgConsts.DOUBLE * Math.asin(Math.sin(dis / (MsgConsts.DOUBLE * r)) / Math.cos(latitude * Math.PI / MsgConsts.DEGREES));
            dlng = dlng * MsgConsts.DEGREES / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * MsgConsts.DEGREES / Math.PI;
            double minlat = latitude - dlat;
            double maxlat = latitude + dlat;
            double minlng = longitude - dlng;
            double maxlng = longitude + dlng;
            queryWrapper.and(wrapper -> wrapper.ge("hl.longitude", minlng).le("hl.longitude", maxlng).ge("hl.latitude", minlat).le("hl.latitude", maxlat));
        }
        /*区域查询*/
        if (longitude != null && latitude != null && kilometer == 1) {
            double r = MsgConsts.RADIUS;//地球半径千米
            double dis = kilometer;//1千米距离
            double dlng = MsgConsts.DOUBLE * Math.asin(Math.sin(dis / (MsgConsts.DOUBLE * r)) / Math.cos(latitude * Math.PI / MsgConsts.DEGREES));
            dlng = dlng * MsgConsts.DEGREES / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * MsgConsts.DEGREES / Math.PI;
            double minlat = latitude - dlat;
            double maxlat = latitude + dlat;
            double minlng = longitude - dlng;
            double maxlng = longitude + dlng;
            queryWrapper.and(wrapper -> wrapper.ge("hl.longitude", minlng).le("hl.longitude", maxlng).ge("hl.latitude", minlat).le("hl.latitude", maxlat));
        }
        if (longitude != null && latitude != null && kilometer == 2) {
            double r = MsgConsts.RADIUS;//地球半径千米
            double dis = kilometer;//1千米距离
            double dlng = MsgConsts.DOUBLE * Math.asin(Math.sin(dis / (MsgConsts.DOUBLE * r)) / Math.cos(latitude * Math.PI / MsgConsts.DEGREES));
            dlng = dlng * MsgConsts.DEGREES / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * MsgConsts.DEGREES / Math.PI;
            double minlat = latitude - dlat;
            double maxlat = latitude + dlat;
            double minlng = longitude - dlng;
            double maxlng = longitude + dlng;
            queryWrapper.and(wrapper -> wrapper.ge("hl.longitude", minlng).le("hl.longitude", maxlng).ge("hl.latitude", minlat).le("hl.latitude", maxlat));
        }
        if (longitude != null && latitude != null && kilometer == 3) {
            double r = MsgConsts.RADIUS;//地球半径千米
            double dis = kilometer;//1千米距离
            double dlng = MsgConsts.DOUBLE * Math.asin(Math.sin(dis / (MsgConsts.DOUBLE * r)) / Math.cos(latitude * Math.PI / MsgConsts.DEGREES));
            dlng = dlng * MsgConsts.DEGREES / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * MsgConsts.DEGREES / Math.PI;
            double minlat = latitude - dlat;
            double maxlat = latitude + dlat;
            double minlng = longitude - dlng;
            double maxlng = longitude + dlng;
            queryWrapper.and(wrapper -> wrapper.ge("hl.longitude", minlng).le("hl.longitude", maxlng).ge("hl.latitude", minlat).le("hl.latitude", maxlat));
        }
        /*类型*/
        if (leaseTypeValue != null || "".equals(leaseTypeValue)) {
            queryWrapper.eq("sd2.dic_value", leaseTypeValue);
        }
        /*租金*/
        if (minRent != null || "".equals(minRent) && maxRent != null || "".equals(maxRent)) {
            queryWrapper.between("hi.rental_month", minRent, maxRent);
        }
        /*写字楼类型*/
        if (pureOffice != null || "".equals(pureOffice)) {
            queryWrapper.and(wrapper -> wrapper.
                    eq("sd3.dic_value", pureOffice).
                    or().
                    eq("sd3.dic_value", commercialComplex).
                    or().
                    eq("sd3.dic_value", businessApartment).
                    or().
                    eq("sd3.dic_value", businessHotel).
                    or().
                    eq("sd3.dic_value", corporateOffice).
                    or().
                    eq("sd3.dic_value",compoundOfficeBuilding));

        }
        /*性质*/
        if (natureTypeValue != null || "".equals(natureTypeValue)) {
            queryWrapper.eq("sd4.dic_value", natureTypeValue);
        }
        /*楼层高度*/
        if (floorHeightTypeValue != null || "".equals(floorHeightTypeValue)) {
            queryWrapper.eq("sd5.dic_value", floorHeightTypeValue);
        }
        /*注册+分割*/
        if (registeredTypeValue != null || "".equals(registeredTypeValue) || segmentationTypeValue != null || "".equals(segmentationTypeValue)) {
            queryWrapper.and(wrapper -> wrapper.eq("sd6.dic_value", registeredTypeValue).or().eq("sd7.dic_value", segmentationTypeValue));
        }
        List<ObInfoVo> roleList = obInfoMapper.getAppObResourceSelectListPage(queryWrapper,userLongitude,userLatitude);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obInfoMapper.getAppObResourceSelectListPage(queryWrapper,userLongitude,userLatitude);
        List<ObInfoVo> arrayList = new ArrayList<>();
        for (ObInfoVo ObInfoVo : roleList) {
            if (!isEmpty(ObInfoVo.getCarouselUrl())) {
                ObInfoVo.setCarouselUrl(fileUtils.getManyUrl(ObInfoVo.getCarouselUrl()));
                String carouselUrl = ObInfoVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                ObInfoVo.setCarouselUrl(substring);
            }
            arrayList.add(ObInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }


    @Override
    public ObFollowHouse getAppObResourceFollowStatusById(Long id, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hfh.ob_info_id", id);
        queryWrapper.eq("hfh.user_id", userId);
        return obInfoMapper.getAppObResourceFollowStatusById(queryWrapper);
    }

    @Override
    public ObInfo getAppObResourceAttentionById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hi.id", id);
        return obInfoMapper.getAppObResourceAttentionById(queryWrapper);
    }

    /**
     * 查询当前userId和房屋id有没有在关注表里面
     * @param id
     * @param userId
     * @return
     */
    @Override
    public ObFollowHouse getAppObInfoFollowById(Long id, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hfh.ob_info_id", id);
        queryWrapper.eq("hfh.user_id", userId);
        return obInfoMapper.getAppObInfoFollowById(queryWrapper);
    }

    @Override
    public String getAppObPersonalCentreListPage(Long pageNum, Long pageSize,Long userId,Double userLongitude, Double userLatitude) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObInfoVo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 176).ge("sd2.id", 171);
        queryWrapper.le("sd3.id", 170).ge("sd3.id", 169);
        queryWrapper.eq("hfh.attention_status", 1);// 根据状态查询
        queryWrapper.eq("hfh.user_id",userId);
        List<ObInfoVo> roleList = obInfoMapper.getAppObPersonalCentreListPage(queryWrapper,userLongitude, userLatitude);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obInfoMapper.getAppObPersonalCentreListPage(queryWrapper,userLongitude, userLatitude);

        List<ObInfoVo> arrayList = new ArrayList<>();
        for (ObInfoVo ObInfoVo : roleList) {
            if (!isEmpty(ObInfoVo.getCarouselUrl())) {
                ObInfoVo.setCarouselUrl(fileUtils.getManyUrl(ObInfoVo.getCarouselUrl()));
                String carouselUrl = ObInfoVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                ObInfoVo.setCarouselUrl(substring);
            }
            arrayList.add(ObInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }




    @Override
    public String getIdLongitudeLatitude(Double userLongitude,Double userLatitude,Double houseLongitude,Double houseLatitude) throws Exception {
        JSONObject object = new JSONObject();
        Map<String,Object> select = obInfoMapper.getIdLongitudeLatitude(userLongitude,userLatitude,houseLongitude,houseLatitude);
        System.out.println(select);
        object.put("dataList", select);
        return object.toString();
    }


    @Override
    public ObInfo getAppObInfoOrderSelectById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("oi.id", id);
        return obInfoMapper.getAppObInfoOrderSelectById(queryWrapper);
    }

}
