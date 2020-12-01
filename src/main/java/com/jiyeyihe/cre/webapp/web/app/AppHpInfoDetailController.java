package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.service.IHpAppointmentHouseService;
import com.jiyeyihe.cre.webapp.service.IHpFollowHouseService;
import com.jiyeyihe.cre.webapp.service.IHpInfoDetailService;
import com.jiyeyihe.cre.webapp.service.IHpResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app租房首页信息展示")
@RestController
@RequestMapping("app/appHouseHomeInfoDetail")
public class AppHpInfoDetailController {

    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private IHpResourcesService iHpResourcesService;
    @Resource
    private IHpFollowHouseService iHpFollowHouseService;
    @Resource
    private IHpAppointmentHouseService iHpAppointmentHouseService;

    @PostMapping("getAppHpInfoDetailListPage")
    @ApiOperation(httpMethod = "POST", value = "租房首页列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "cityName", value = "市区", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userLongitude", value = "用户经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLatitude", value = "用户纬度", required = true, dataType = "Double", paramType = "query")})
    public Result getAppHpInfoDetailListPage(Long pageNum, Long pageSize, String cityName,Double userLongitude,Double userLatitude) {
        Result result = null;
        try {
            String resultData = iHpInfoDetailService.getAppHpInfoDetailListPage(pageNum,pageSize,cityName,userLongitude,userLatitude);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "查询单个房源详情")
    @GetMapping("getIdAppHpInfoDetail")
    public Result getIdAppHpInfoDetail(@RequestParam("id")Long id,Long userId) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if(userId!=null){
                HpFollowHouse followHouse = new HpFollowHouse();
                HpAppointmentHouse appointmentHouse = new HpAppointmentHouse();
                QueryWrapper queryWrapper = new QueryWrapper();
                //查询关注表和看房记录有没有这个用户操作过
                HpFollowHouse followById = iHpInfoDetailService.getAppHpResourceFollowStatusById(id, userId);
                queryWrapper.eq("hp_info_id",id);
                queryWrapper.eq("user_id",userId);
                HpAppointmentHouse appointmentById = iHpAppointmentHouseService.getOne(queryWrapper);
                //查询当前进入的房屋id
                HpInfo infoById = iHpInfoDetailService.getAppHpResourceAttentionById(id);
                if (followById==null){//因为用户进来会显示是否关注
                    followHouse.setUserId(userId);
                    followHouse.setAttentionStatus(MsgConsts.ZERO_STATUS);
                    followHouse.setHpInfoId(infoById.getId());
                    iHpFollowHouseService.save(followHouse);
                }if (appointmentById==null){//因为用户进来会显示是否有看过房
                    appointmentHouse.setUserId(userId);
                    appointmentHouse.setAppointmentStatus(MsgConsts.ZERO_STATUS);
                    appointmentHouse.setHpInfoId(infoById.getId());
                    appointmentHouse.setCreatetime(System.currentTimeMillis());
                    appointmentHouse.setUpdatetime(System.currentTimeMillis());
                    iHpAppointmentHouseService.save(appointmentHouse);
                }
                String selectListById = iHpInfoDetailService.getAppHpInfoSelectById(id,userId);
                JSONObject jsonObject = JSON.parseObject(selectListById);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
            }else{

                String selectListById = iHpInfoDetailService.getAppHpInfoIdSelectById(id);
                JSONObject jsonObject = JSON.parseObject(selectListById);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getAppHpResourceSelectListPage")
    @ApiOperation(httpMethod = "POST", value = "租房房源列表多条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "communityName", value = "小区名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "provinceName", value = "省名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cityName", value = "市名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "districtName", value = "区名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "leaseTypeValue", value = "租房类型 整租/合租", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "minRent", value = "最小租金", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "maxRent", value = "最大租金", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "floorHeightTypeValue", value = "楼层高度", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "viewingTimeValue", value = "看房时间", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "renovationConditionValue", value = "装修情况", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "payWayTypeValue", value = "付款方式", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "houseOrientationTypeValue", value = "房屋朝向", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "houseTypeValue", value = "房型", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "television", value = "电视", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "washingMachine", value = "洗衣机", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "airConditioner", value = "空调", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "kilometer", value = "公里范围", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "经度", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "latitude", value = "纬度", required = false, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLongitude", value = "用户经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLatitude", value = "用户纬度", required = true, dataType = "Double", paramType = "query")})
    public Result getAppHpResourceSelectListPage(Long pageNum, Long pageSize, String communityName,
                                                   String provinceName,String cityName,String districtName,
                                                   Integer leaseTypeValue,
                                                   String minRent,String maxRent,
                                                   Integer floorHeightTypeValue,
                                                   Integer viewingTimeValue,Integer renovationConditionValue,
                                                   Integer payWayTypeValue,Integer houseOrientationTypeValue,
                                                   Integer houseTypeValue,
                                                   Integer television,Integer washingMachine,Integer airConditioner,
                                                   Integer kilometer,
                                                   Double longitude,Double latitude,
                                                   Double userLongitude, Double userLatitude) {
        Result result = null;
        try {
            String resultData = iHpInfoDetailService.getAppHpResourceSelectListPage(pageNum,pageSize,communityName,provinceName,cityName,districtName,leaseTypeValue,minRent,maxRent,floorHeightTypeValue,viewingTimeValue,renovationConditionValue,payWayTypeValue,houseOrientationTypeValue,houseTypeValue,television,washingMachine,airConditioner,kilometer,longitude,latitude,userLongitude,userLatitude);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "修改app房源关注")
    @RequestMapping(value = "updateAppHpResourceAttention")
    public Result updateAppHpResourceAttention(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        //获取到当前房屋信息id，将房屋信息id插入到关注表里面
        try {
            HpFollowHouse followHouse = new HpFollowHouse();
            HpInfo byId = iHpInfoDetailService.getAppHpResourceAttentionById(id);
            //查询单个房屋信息id
            HpFollowHouse followById = iHpInfoDetailService.getAppHpInfoFollowById(id, userId);
            if (followById==null){
                followHouse.setUserId(userId);
                followHouse.setAttentionStatus(MsgConsts.FIRST_STATUS);
                followHouse.setHpInfoId(byId.getId());
                iHpFollowHouseService.save(followHouse);
                HpFollowHouse houseFollowHouse = iHpFollowHouseService.getAppHpResourceFollowById(id,userId);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,houseFollowHouse);
            }else{
                if (followById.getAttentionStatus()==MsgConsts.ZERO_STATUS){
                    followById.setAttentionStatus(MsgConsts.FIRST_STATUS);
                }else{
                    followById.setAttentionStatus(MsgConsts.ZERO_STATUS);
                }
                iHpFollowHouseService.updateById(followById);
                HpFollowHouse houseFollowHouse = iHpFollowHouseService.getAppHpResourceFollowById(id,userId);
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,houseFollowHouse);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "发布app房源评价")
    @RequestMapping(value = "addAppHpInfoComment")
    public Result addAppHpInfoComment(@RequestBody HpComment hpComment, Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpComment.setCreatetime(System.currentTimeMillis());
            hpComment.setUpdatetime(System.currentTimeMillis());
            int update = iHpResourcesService.addAppHpInfoComment(hpComment, id);
            if (update == MsgConsts.SUCCESS_CODE){
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_CODE);
            }else{
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_CODE);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



}
