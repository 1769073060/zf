package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app写字楼首页信息展示")
@RestController
@RequestMapping("app/appOfficeBuildingHomeInfoDetail")
public class AppObInfoDetailController {

    @Resource
    private IObInfoDetailService iObInfoDetailService;
    @Resource
    private IObFollowHouseService iObFollowHouseService;
    @Resource
    private IObAppointmentHouseService iObAppointmentHouseService;

    @PostMapping("getAppObInfoDetailListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼首页列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "cityName", value = "市区", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userLongitude", value = "用户经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLatitude", value = "用户纬度", required = true, dataType = "Double", paramType = "query")})
    public Result getAppObInfoDetailListPage(Long pageNum, Long pageSize, String cityName,Double userLongitude,Double userLatitude) {
        Result result = null;
        try {
            String resultData = iObInfoDetailService.getAppObInfoDetailListPage(pageNum,pageSize,cityName,userLongitude,userLatitude);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "查询单个写字楼详情")
    @GetMapping("getIdAppObInfoDetail")
    public Result getIdAppObInfoDetail(@RequestParam("id")Long id,Long userId) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if(userId!=null){
                ObFollowHouse followHouse = new ObFollowHouse();
                ObAppointmentHouse appointmentHouse = new ObAppointmentHouse();
                QueryWrapper queryWrapper = new QueryWrapper();
                //查询关注表和看房记录有没有这个用户操作过
                ObFollowHouse followById = iObInfoDetailService.getAppObResourceFollowStatusById(id, userId);
                queryWrapper.eq("ob_info_id",id);
                queryWrapper.eq("user_id",userId);
                ObAppointmentHouse appointmentById = iObAppointmentHouseService.getOne(queryWrapper);
                //查询当前进入的房屋id
                ObInfo infoById = iObInfoDetailService.getAppObResourceAttentionById(id);
                if (followById==null){//因为用户进来会显示是否关注
                    followHouse.setUserId(userId);
                    followHouse.setAttentionStatus(MsgConsts.ZERO_STATUS);
                    followHouse.setObInfoId(infoById.getId());
                    iObFollowHouseService.save(followHouse);
                }if (appointmentById==null){//因为用户进来会显示是否有看过房
                    appointmentHouse.setUserId(userId);
                    appointmentHouse.setAppointmentStatus(MsgConsts.ZERO_STATUS);
                    appointmentHouse.setObInfoId(infoById.getId());
                    appointmentHouse.setCreatetime(System.currentTimeMillis());
                    appointmentHouse.setUpdatetime(System.currentTimeMillis());
                    iObAppointmentHouseService.save(appointmentHouse);
                }
                String selectListById = iObInfoDetailService.getAppObInfoSelectById(id,userId);
                JSONObject jsonObject = JSON.parseObject(selectListById);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
            }else {
                String selectListById = iObInfoDetailService.getAppObInfoIdSelectById(id);
                JSONObject jsonObject = JSON.parseObject(selectListById);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getAppObResourceSelectListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼房源列表多条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "propertyName", value = "楼盘名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "provinceName", value = "省名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cityName", value = "市名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "districtName", value = "区名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "kilometer", value = "公里范围", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "latitude", value = "纬度", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "minRent", value = "最小租金", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "maxRent", value = "最大租金", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "leaseTypeValue", value = "租赁类型 一层/单间", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pureOffice", value = "纯写字楼", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "commercialComplex", value = "商业综合体", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "businessApartment", value = "商务公寓", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "businessHotel", value = "商务酒店", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "corporateOffice", value = "企业自用写字楼", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "compoundOfficeBuilding", value = "复合型写字楼", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "natureTypeValue", value = "性质：1.新房，2.二手房", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "floorHeightTypeValue", value = "楼层高度：1.低，2.中，3.高", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "registeredTypeValue", value = "可注册", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "segmentationTypeValue", value = "可分割", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userLongitude", value = "用户经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLatitude", value = "用户纬度", required = true, dataType = "Double", paramType = "query")})
    public Result getAppObResourceSelectListPage(Long pageNum, Long pageSize,
                                                      String propertyName, String provinceName, String cityName, String districtName,
                                                      Integer kilometer, Double longitude, Double latitude,
                                                      Integer minRent, Integer maxRent,
                                                      Integer leaseTypeValue,
                                                      Integer pureOffice,Integer commercialComplex,Integer businessApartment,Integer businessHotel,Integer corporateOffice,Integer compoundOfficeBuilding,
                                                      Integer natureTypeValue,
                                                      Integer floorHeightTypeValue,Integer registeredTypeValue,
                                                      Integer segmentationTypeValue,
                                                      Double userLongitude, Double userLatitude) {
        Result result = null;
        try {
            String resultData = iObInfoDetailService.getAppObResourceSelectListPage( pageNum,  pageSize, propertyName,  provinceName,  cityName,  districtName, kilometer, longitude,  latitude, minRent,  maxRent, leaseTypeValue, pureOffice, commercialComplex, businessApartment, businessHotel, corporateOffice, compoundOfficeBuilding, natureTypeValue, floorHeightTypeValue, registeredTypeValue, segmentationTypeValue,userLongitude,userLatitude);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "修改app写字楼关注")
    @RequestMapping(value = "updateAppObResourceAttention")
    public Result updateAppObResourceAttention(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        //获取到当前房屋信息id，将房屋信息id插入到关注表里面
        try {
            ObFollowHouse followHouse = new ObFollowHouse();
            ObInfo byId = iObInfoDetailService.getAppObResourceAttentionById(id);
            //查询单个房屋信息id
            ObFollowHouse followById = iObInfoDetailService.getAppObInfoFollowById(id, userId);
            if (followById==null){
                followHouse.setUserId(userId);
                followHouse.setAttentionStatus(MsgConsts.FIRST_STATUS);
                followHouse.setObInfoId(byId.getId());
                iObFollowHouseService.save(followHouse);
                ObFollowHouse houseFollowHouse = iObFollowHouseService.getAppObResourceFollowById(id,userId);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,houseFollowHouse);
            }else{
                if (followById.getAttentionStatus()==MsgConsts.ZERO_STATUS){
                    followById.setAttentionStatus(MsgConsts.FIRST_STATUS);
                }else{
                    followById.setAttentionStatus(MsgConsts.ZERO_STATUS);
                }
                iObFollowHouseService.updateById(followById);
                ObFollowHouse houseFollowHouse = iObFollowHouseService.getAppObResourceFollowById(id,userId);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,houseFollowHouse);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getIdLongitudeLatitude")
    @ApiOperation(httpMethod = "POST", value = "获取用户经纬度和房屋经纬度查询公里")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLongitude", value = "用户经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLatitude", value = "用户纬度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "houseLongitude", value = "房子经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "houseLatitude", value = "房子纬度", required = true, dataType = "Double", paramType = "query"),
    })
    public Result getIdLongitudeLatitude(Double userLongitude,Double userLatitude,Double houseLongitude,Double houseLatitude) {
        Result result = null;
        if (isEmpty( userLatitude, userLongitude)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iObInfoDetailService.getIdLongitudeLatitude(userLongitude,userLatitude,houseLongitude,houseLatitude);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
