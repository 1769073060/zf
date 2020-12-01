package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.entity.Param;
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
@Api(tags = "app我发布的房源")
@RestController
@RequestMapping("app/appHouseResource")
public class AppHpResourceController {

    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private IHpResourcesService iHpResourcesService;
    @Resource
    private IHpLocationService iHpLocationService;
    @Resource
    private IHpFurnitureService iHpFurnitureService;
    @Resource
    private IHpRentService iHpRentService;
    @Resource
    private IHpRequirementsService iHpRequirementsService;

    @ApiOperation(httpMethod = "POST", value = "我发布的房源添加房产区域")
    @PostMapping(value = "addAppHpLocation")
    public Result addAppHpLocation(@RequestBody HpLocation hpLocation){
        Result result = null;
        Object[] objs={hpLocation.getCommunityName(), hpLocation.getBuilding(), hpLocation.getAddress(),hpLocation.getDescrip(),hpLocation.getElevator(),hpLocation.getParkingSpace(),hpLocation.getUserId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if(hpLocation.getBusinessId()==null){
                hpLocation.setBusinessId(MsgConsts.BusinessId_Status);
            }
            hpLocation.setCreatetime(System.currentTimeMillis());
            hpLocation.setUpdatetime(System.currentTimeMillis());
            iHpLocationService.save(hpLocation);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }

    @PostMapping("getAppHpResourcesListPage")
    @ApiOperation(httpMethod = "POST", value = "我发布的房源信息列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "Long", paramType = "query")})
    public Result getAppHpResourcesListPage(Long pageNum, Long pageSize,Long userId) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpResourcesService.getAppHpResourceSelectListPage(pageNum,pageSize,userId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "添加我发布的房源")
    @PostMapping(value = "addAppHpResource")
    public Result addAppHpResource(@RequestBody Param param
    ){
        Result result = null;
        Object[] objs={param.getHpFurniture(),param.getHpInfo(),param.getHpRent(),param.getHpRequirements(),param.hpInfo.getUserId(),param.hpFurniture.getUserId(),param.hpRequirements.getUserId(),param.hpRent.getUserId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if (param.hpInfo.getBedroomType() == null && param.hpInfo.getBedroomFacingType() == null) {
                param.hpInfo.setBedroomType(MsgConsts.ZERO_STATUS);
                param.hpInfo.setBedroomFacingType(MsgConsts.ZERO_STATUS);
            }
            param.hpInfo.setBusinessId(MsgConsts.BusinessId_Status);
            param.hpInfo.setAttentionStatus(MsgConsts.ZERO_STATUS);
            param.hpInfo.setCreatetime(System.currentTimeMillis());
            param.hpInfo.setUpdatetime(System.currentTimeMillis());
            boolean info = iHpInfoDetailService.addHpInfoDetailInfo(param.hpInfo);
            if (info==true){
                //获取最大值id
                HpInfo maxId = iHpInfoDetailService.listMaxId();
                param.hpFurniture.setBusinessId(MsgConsts.BusinessId_Status);
                param.hpRent.setBusinessId(MsgConsts.BusinessId_Status);
                param.hpRequirements.setBusinessId(MsgConsts.BusinessId_Status);
                param.hpFurniture.setHpInfoId(maxId.getId());
                param.hpRent.setHpInfoId(maxId.getId());
                param.hpRequirements.setHpInfoId(maxId.getId());
                param.hpFurniture.setCreatetime(System.currentTimeMillis());
                param.hpFurniture.setUpdatetime(System.currentTimeMillis());
                param.hpRent.setCreatetime(System.currentTimeMillis());
                param.hpRent.setUpdatetime(System.currentTimeMillis());
                param.hpRequirements.setCreatetime(System.currentTimeMillis());
                param.hpRequirements.setUpdatetime(System.currentTimeMillis());
                String infoDetail = iHpInfoDetailService.addHpInfoDetail(param.hpFurniture, param.hpRent, param.hpRequirements);
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,infoDetail);
            }else {
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, MsgConsts.FAIL_MSG);
            }
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }

    @PostMapping("updateHpInfoDetail")
    @ApiOperation(httpMethod = "POST", value = "更新房屋信息")
    public Result updateHpInfoDetail(@RequestBody Param param
    ){
        Result result = null;
        if (isEmpty(param.hpInfo.getId(),param.hpInfo.getUserId(),param.hpFurniture.getUserId(),param.hpRequirements.getUserId(),param.hpRent.getUserId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            param.hpInfo.setUpdatetime(System.currentTimeMillis());
            param.hpFurniture.setUpdatetime(System.currentTimeMillis());
            param.hpRent.setUpdatetime(System.currentTimeMillis());
            param.hpRequirements.setUpdatetime(System.currentTimeMillis());
            HpInfo houseInfo = iHpInfoDetailService.getById(param.hpInfo.getId());
            houseInfo = (HpInfo) ObjectConvert.combineObjectCore(param.hpInfo,houseInfo);
            iHpInfoDetailService.updateById(houseInfo);
            iHpFurnitureService.updateHpInfoDetailFurnitureByLocationId(param.hpInfo,param.hpFurniture);
            iHpRentService.updateHpInfoDetailRentByLocationId(param.hpInfo,param.hpRent);
            iHpRequirementsService.updateHpInfoDetailRequirementsByLocationId(param.hpInfo,param.hpRequirements);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "查询单个我发布房源信息")
    @GetMapping("getIdAppHpInfoDetail")
    public Result getIdAppHpInfoDetail(@RequestParam("id")Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            String selectListById = iHpInfoDetailService.getAppHpInfoDetailSelectById(id,userId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
