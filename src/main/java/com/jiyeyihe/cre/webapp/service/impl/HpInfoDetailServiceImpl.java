package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;

import com.jiyeyihe.cre.webapp.mapper.HpInfoMapper;
import com.jiyeyihe.cre.webapp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;
import static java.lang.Math.*;

@Slf4j
@Service
@Transactional
public class HpInfoDetailServiceImpl extends ServiceImpl<HpInfoMapper, HpInfo> implements IHpInfoDetailService {

    @Resource
    private HpInfoMapper hpInfoMapper;
    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private IHpFurnitureService iHpFurnitureService;
    @Resource
    private IHpRentService iHpRentService;
    @Resource
    private IHpRequirementsService iHpRequirementsService;
    @Resource
    private FileUtils fileUtils;

    @Override
    public String getHpInfoDetailListPage(Long pageNum, Long pageSize, String communityName, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        if (communityName != null || "".equals(communityName)) {
            queryWrapper.like("community_name", communityName).or().like("house_number", communityName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        List<HpInfoVo> roleList = hpInfoMapper.getHpInfoDetailSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpInfoMapper.getHpInfoDetailSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public String getHpInfoDetailListCommunityName(Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hl.business_id",businessId);
        }
        queryWrapper.orderByDesc("id");
        List<HpLocation> roleList = hpInfoMapper.getHpInfoDetailSelectCommunityName(queryWrapper);
        object.put("dataList", roleList);
        return object.toString();
    }

    /**
     * 插入主表
     *
     * @param hpInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean addHpInfoDetailInfo(HpInfo hpInfo) throws Exception {
        if (hpInfo.getFloor() >= MsgConsts.ZERO_STATUS && hpInfo.getFloor() <= MsgConsts.THIRD_STATUS) {
            hpInfo.setFloorHeight(MsgConsts.FIRST_STATUS);
        }
        if (hpInfo.getFloor() >= MsgConsts.FOURTH_STATUS && hpInfo.getFloor() <= MsgConsts.SIXTH_STATUS) {
            hpInfo.setFloorHeight(MsgConsts.SECOND_STATUS);
        }
        if (hpInfo.getFloor() >= MsgConsts.SEVENTH_STATUS) {
            hpInfo.setFloorHeight(MsgConsts.THIRD_STATUS);
        }
        if (hpInfo.getBedroom() == MsgConsts.FIRST_STATUS && hpInfo.getDrawingRoom() == MsgConsts.FIRST_STATUS) {
            hpInfo.setHouseType(MsgConsts.FIRST_STATUS);
        }
        if (hpInfo.getBedroom() == MsgConsts.SECOND_STATUS && hpInfo.getDrawingRoom() == MsgConsts.FIRST_STATUS) {
            hpInfo.setHouseType(MsgConsts.SECOND_STATUS);
        }
        if (hpInfo.getBedroom() == MsgConsts.SECOND_STATUS && hpInfo.getDrawingRoom() == MsgConsts.SECOND_STATUS) {
            hpInfo.setHouseType(MsgConsts.THIRD_STATUS);
        }
        if (hpInfo.getBedroom() == MsgConsts.THIRD_STATUS && hpInfo.getDrawingRoom() == MsgConsts.FIRST_STATUS) {
            hpInfo.setHouseType(MsgConsts.FOURTH_STATUS);
        }
        if (hpInfo.getBedroom() == MsgConsts.THIRD_STATUS && hpInfo.getDrawingRoom() == MsgConsts.SECOND_STATUS) {
            hpInfo.setHouseType(MsgConsts.FIFTH_STATUS);
        }
        boolean save = iHpInfoDetailService.save(hpInfo);
        if (save == true) {
            return true;
        }
        return false;
    }

    /**
     * 插入其余三张关联表
     *
     * @param hpFurniture
     * @param hpRent
     * @param hpRequirements
     * @return
     * @throws Exception
     */
    @Override
    public String addHpInfoDetail(HpFurniture hpFurniture, HpRent hpRent, HpRequirements hpRequirements) throws Exception {
        boolean furnitureSave = iHpFurnitureService.save(hpFurniture);
        boolean rentSave = iHpRentService.save(hpRent);
        boolean requirementsSave = iHpRequirementsService.save(hpRequirements);
        if (furnitureSave == true && rentSave == true && requirementsSave == true) {
            return "添加成功";
        } else {
            return "添加失败";
        }
    }

    /**
     * 查询当前信息表id的最大值
     *
     * @return
     */
    @Override
    public HpInfo listMaxId() {
        HpInfo selectMaxId = hpInfoMapper.getHpInfoDetailSelectMaxId();
        return selectMaxId;
    }


    @Override
    public String getHpInfoDetailSelectById(Long id,Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        HpInfo HpInfo = hpInfoMapper.selectById(id);
        HpInfo.setCarouselUrl(fileUtils.getManyUrl(HpInfo.getCarouselUrl()));
        HpInfo.setVrPicturesUrl(fileUtils.getIpaURl(HpInfo.getVrPicturesUrl()));
        HpInfo.setVideoUrl(fileUtils.getIpaURl(HpInfo.getVideoUrl()));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hi.id", id);
        queryWrapper.eq("hi.business_id", businessId);
        Map<String, Object> selectById = hpInfoMapper.getHpInfoDetailSelectById(queryWrapper);
        selectById.put("carouselUrl", HpInfo.getCarouselUrl());
        selectById.put("vrPicturesUrl", HpInfo.getVrPicturesUrl());
        selectById.put("videoUrl", HpInfo.getVideoUrl());
        object.put("dataList", selectById);
        return object.toString();
    }


    @Override
    public String getAppHpInfoDetailListPage(Long pageNum, Long pageSize, String cityName,Double userLongitude,Double userLatitude) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        if (cityName != null || "".equals(cityName)) {
            queryWrapper.like("sc.city_name", cityName);
        }
        queryWrapper.le("sd.id", 119).ge("sd.id", 118);
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 147).ge("sd2.id", 145);
        queryWrapper.le("sd3.id", 158).ge("sd3.id", 148);
        queryWrapper.eq("hres.house_resources_status", 1);// 根据状态查询
        List<HpInfoVo> roleList = hpInfoMapper.getAppHpInfoDetailSelectList(queryWrapper,userLongitude,userLatitude);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
//        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpInfoMapper.getAppHpInfoDetailSelectList(queryWrapper,userLongitude,userLatitude);
        List<HpInfoVo> arrayList = new ArrayList<>();
        for (HpInfoVo hpInfoVo : roleList) {
            if (!isEmpty(hpInfoVo.getCarouselUrl())) {
                hpInfoVo.setCarouselUrl(fileUtils.getManyUrl(hpInfoVo.getCarouselUrl()));
                String carouselUrl = hpInfoVo.getCarouselUrl();
                String url = hpInfoVo.getCarouselUrl();
                int hpUrl = url.length()-url.replace(",","").length();
                int i = (hpUrl-2);
                //如果图片超过3张返回张数
                hpInfoVo.setQuantityStatus(i);
                if (i>0){
                    int indexOf = carouselUrl.indexOf(",");
                    int indexOf2 = carouselUrl.indexOf(",",indexOf+1);
                    int indexOf3 = carouselUrl.indexOf(",",indexOf2+1);
                    String hpInfoJpg = carouselUrl.substring(0, indexOf3);
                    hpInfoVo.setCarouselUrl(hpInfoJpg);
                }else {
                    hpInfoVo.setCarouselUrl(carouselUrl);
                }

            }
            arrayList.add(hpInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }

    /*获取单个发布我的房源信息*/
    @Override
    public String getAppHpInfoDetailSelectById(Long id,Long userId) throws Exception {
        JSONObject object = new JSONObject();
        HpInfo HpInfo = hpInfoMapper.selectById(id);
        HpInfo.setCarouselUrl(fileUtils.getManyUrl(HpInfo.getCarouselUrl()));
        String videoUrl = HpInfo.getVideoUrl();
        String vrPicturesUrl = HpInfo.getVrPicturesUrl();
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id", 112).ge("sd.id", 109);
        queryWrapper.le("sd2.id", 117).ge("sd2.id", 113);
        queryWrapper.le("sd3.id", 119).ge("sd3.id", 118);
        queryWrapper.le("sd4.id", 123).ge("sd4.id", 120);
        queryWrapper.le("sd5.id", 133).ge("sd5.id", 124);
        queryWrapper.le("sd6.id", 139).ge("sd6.id", 134);
        queryWrapper.le("sd7.id", 144).ge("sd7.id", 140);
        queryWrapper.le("sd8.id", 147).ge("sd8.id", 145);
        queryWrapper.le("sd9.id", 158).ge("sd9.id", 148);
        queryWrapper.le("sd10.id", 161).ge("sd10.id", 159);
        queryWrapper.eq("hi.id", id);
        queryWrapper.eq("hi.user_id",userId);
        Map<String, Object> selectById = hpInfoMapper.getAppHpInfoResourceSelectById(queryWrapper);
        selectById.put("carouselUrl", HpInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            HpInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", HpInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            HpInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", HpInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public HpFollowHouse getAppHpResourceFollowStatusById(Long id, Long userId) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("hfh.hp_info_id", id);
            queryWrapper.eq("hfh.user_id", userId);
            return hpInfoMapper.getAppHpResourceFollowStatusById(queryWrapper);
    }


    /*根据userId获取房源信息*/
    @Override
    public String getAppHpInfoSelectById(Long id,Long userId) throws Exception {
        JSONObject object = new JSONObject();
        HpInfo hpInfo = hpInfoMapper.selectById(id);
        hpInfo.setCarouselUrl(fileUtils.getManyUrl(hpInfo.getCarouselUrl()));
        String videoUrl = hpInfo.getVideoUrl();
        String vrPicturesUrl = hpInfo.getVrPicturesUrl();
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id", 112).ge("sd.id", 109);
        queryWrapper.le("sd2.id", 117).ge("sd2.id", 113);
        queryWrapper.le("sd3.id", 119).ge("sd3.id", 118);
        queryWrapper.le("sd4.id", 123).ge("sd4.id", 120);
        queryWrapper.le("sd5.id", 133).ge("sd5.id", 124);
        queryWrapper.le("sd6.id", 139).ge("sd6.id", 134);
        queryWrapper.le("sd7.id", 144).ge("sd7.id", 140);
        queryWrapper.le("sd8.id", 147).ge("sd8.id", 145);
        queryWrapper.le("sd9.id", 158).ge("sd9.id", 148);
        queryWrapper.le("sd10.id", 161).ge("sd10.id", 159);
        queryWrapper.eq("hi.id", id);
        queryWrapper.eq("hfh.hp_info_id", id);
        queryWrapper.eq("hfh.user_id",userId);
        queryWrapper.eq("hah.user_id",userId);
        Map<String, Object> selectById = hpInfoMapper.getAppHpInfoSelectById(queryWrapper);
        selectById.put("carouselUrl", hpInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            hpInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", hpInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            hpInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", hpInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }



    /*获取单个房源信息*/
    @Override
    public String getAppHpInfoIdSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        HpInfo hpInfo = hpInfoMapper.selectById(id);
        hpInfo.setCarouselUrl(fileUtils.getManyUrl(hpInfo.getCarouselUrl()));
        String videoUrl = hpInfo.getVideoUrl();
        String vrPicturesUrl = hpInfo.getVrPicturesUrl();
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id", 112).ge("sd.id", 109);
        queryWrapper.le("sd2.id", 117).ge("sd2.id", 113);
        queryWrapper.le("sd3.id", 119).ge("sd3.id", 118);
        queryWrapper.le("sd4.id", 123).ge("sd4.id", 120);
        queryWrapper.le("sd5.id", 133).ge("sd5.id", 124);
        queryWrapper.le("sd6.id", 139).ge("sd6.id", 134);
        queryWrapper.le("sd7.id", 144).ge("sd7.id", 140);
        queryWrapper.le("sd8.id", 147).ge("sd8.id", 145);
        queryWrapper.le("sd9.id", 158).ge("sd9.id", 148);
        queryWrapper.le("sd10.id", 161).ge("sd10.id", 159);
        queryWrapper.eq("hi.id", id);
        Map<String, Object> selectById = hpInfoMapper.getAppHpInfoIdSelectById(queryWrapper);
        selectById.put("carouselUrl", hpInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            hpInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", hpInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            hpInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", hpInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }


    @Override
    public String getAppHpResourceSelectListPage(Long pageNum, Long pageSize, String communityName,
                                                      String provinceName, String cityName, String districtName,
                                                      Integer leaseTypeValue,
                                                      String minRent, String maxRent,
                                                      Integer floorHeightTypeValue,
                                                      Integer viewingTimeValue, Integer renovationConditionValue,
                                                      Integer payWayTypeValue, Integer houseOrientationTypeValue,
                                                      Integer houseTypeValue,
                                                      Integer television, Integer washingMachine, Integer airConditioner,
                                                      Integer kilometer,
                                                      Double longitude, Double latitude,
                                                      Double userLongitude,Double userLatitude) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        if (communityName != null || "".equals(communityName)) {
            queryWrapper.like("hi.community_name", communityName);
        }
        queryWrapper.le("sd.id", 112).ge("sd.id", 109);
        queryWrapper.le("sd2.id", 117).ge("sd2.id", 113);
        queryWrapper.le("sd3.id", 119).ge("sd3.id", 118);
        queryWrapper.le("sd4.id", 123).ge("sd4.id", 120);
        queryWrapper.le("sd5.id", 133).ge("sd5.id", 124);
        queryWrapper.le("sd6.id", 139).ge("sd6.id", 134);
        queryWrapper.le("sd7.id", 144).ge("sd7.id", 140);
        queryWrapper.le("sd8.id", 147).ge("sd8.id", 145);
        queryWrapper.le("sd9.id", 158).ge("sd9.id", 148);
        queryWrapper.le("sd10.id", 161).ge("sd10.id", 159);
        queryWrapper.le("sd11.id", 168).ge("sd11.id", 164);
        queryWrapper.eq("hres.house_resources_status", 1);// 根据状态查询
        /*区域查询*/
        if (provinceName != null || "".equals(provinceName)) {
            queryWrapper.eq("sp.id", provinceName);
        }
        if (cityName != null || "".equals(cityName)) {
            queryWrapper.eq("sc.id", cityName);
        }
        if (districtName != null || "".equals(districtName)){
            queryWrapper.eq("sds.id", districtName);
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
        /*类型*/
        if (leaseTypeValue != null || "".equals(leaseTypeValue)) {
            queryWrapper.eq("sd3.dic_value", leaseTypeValue);
        }
        /*租金*/
        if (minRent != null || "".equals(minRent) && maxRent != null || "".equals(maxRent)) {
            queryWrapper.between("rental", minRent, maxRent);
        }
        /*筛选*/
        if (viewingTimeValue != null || "".equals((viewingTimeValue)) || renovationConditionValue != null || "".equals((renovationConditionValue)) || payWayTypeValue != null || "".equals((payWayTypeValue))) {
            queryWrapper.and(wrapper -> wrapper.eq("sd.dic_value", viewingTimeValue).or().eq("sd4.dic_value", renovationConditionValue).or().eq("sd6.dic_value", payWayTypeValue));
        }
        if (houseOrientationTypeValue != null || "".equals(houseOrientationTypeValue)) {
            queryWrapper.eq("sd5.dic_value", houseOrientationTypeValue);
//            queryWrapper.like("hi.community_name",communityName).or().like("hi.house_number",communityName);
        }
        /*楼层 低中高*/
        if (floorHeightTypeValue != null || "".equals(floorHeightTypeValue)) {
            queryWrapper.eq("sd10.dic_value", floorHeightTypeValue);
        }
        /*户型*/
        if (houseTypeValue != null || "".equals(houseTypeValue)) {
            queryWrapper.eq("sd11.dic_value", houseTypeValue);
        }
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
        List<HpInfoVo> roleList = hpInfoMapper.getAppHpResourceSelectListPage(queryWrapper,userLongitude,userLatitude);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpInfoMapper.getAppHpResourceSelectListPage(queryWrapper,userLongitude,userLatitude);
        List<HpInfoVo> arrayList = new ArrayList<>();
        for (HpInfoVo HpInfoVo : roleList) {
            if (!isEmpty(HpInfoVo.getCarouselUrl())) {
                HpInfoVo.setCarouselUrl(fileUtils.getManyUrl(HpInfoVo.getCarouselUrl()));
                String carouselUrl = HpInfoVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                HpInfoVo.setCarouselUrl(substring);
            }
            arrayList.add(HpInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }


    @Override
    public HpInfo getAppHpResourceAttentionById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hi.id", id);
        return hpInfoMapper.getAppHpResourceAttentionById(queryWrapper);
    }

    /**
     * 查询当前userId和房屋id有没有在关注表里面
     * @param id
     * @param userId
     * @return
     */
    @Override
    public HpFollowHouse getAppHpInfoFollowById(Long id,Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hfh.hp_info_id", id);
        queryWrapper.eq("hfh.user_id", userId);
        return hpInfoMapper.getAppHpInfoFollowById(queryWrapper);
    }



    @Override
    public String getAppHpPersonalCentreListPage(Long pageNum, Long pageSize,Long userId,Double userLongitude, Double userLatitude) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id", 119).ge("sd.id", 118);
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 147).ge("sd2.id", 145);
        queryWrapper.le("sd3.id", 158).ge("sd3.id", 148);
        queryWrapper.eq("hfh.attention_status", 1);// 根据状态查询
        queryWrapper.eq("hfh.user_id",userId);
        List<HpInfoVo> roleList = hpInfoMapper.getAppHpPersonalCentreListPage(queryWrapper,userLongitude,userLatitude);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpInfoMapper.getAppHpPersonalCentreListPage(queryWrapper,userLongitude,userLatitude);

        List<HpInfoVo> arrayList = new ArrayList<>();
        for (HpInfoVo HpInfoVo : roleList) {
            if (!isEmpty(HpInfoVo.getCarouselUrl())) {
                HpInfoVo.setCarouselUrl(fileUtils.getManyUrl(HpInfoVo.getCarouselUrl()));
                String carouselUrl = HpInfoVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                HpInfoVo.setCarouselUrl(substring);
            }
            arrayList.add(HpInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }


    @Override
    public HpInfo getAppHpInfoOrderSelectById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hi.id", id);
        return hpInfoMapper.getAppHpInfoOrderSelectById(queryWrapper);
    }

}

