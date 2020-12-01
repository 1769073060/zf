package com.jiyeyihe.cre.webapp.web.busines;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.cglib.core.$DuplicatesPredicate;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.jiyeyihe.cre.commons.response.Result;
import javax.annotation.Resource;



import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "租房房屋详情")
@RestController
@RequestMapping("business/houseInfoDetail")
public class HpInfoDetailController {
    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private IHpFurnitureService iHpFurnitureService;
    @Resource
    private IHpRentService iHpRentService;
    @Resource
    private IHpRequirementsService iHpRequirementsService;
    @Resource
    private IHpResourcesService iHpResourcesService;


    @PostMapping("getHpInfoDetailListPage")
    @ApiOperation(httpMethod = "POST", value = "房屋详情分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "communityName", value = "小区名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpInfoDetailListPage(Long pageNum,Long pageSize,String communityName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpInfoDetailService.getHpInfoDetailListPage(pageNum,pageSize,communityName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "添加房屋详情")
    @PostMapping(value = "addHpInfoDetail")
    public Result addHpInfoDetails(@RequestBody Param param
    ){
        Result result = null;
        Object[] objs={param.hpInfo.getBusinessId(),param.hpFurniture.getBusinessId(),param.hpRent.getBusinessId(),param.hpRequirements.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if (param.hpInfo.getBedroomType() == null && param.hpInfo.getBedroomFacingType() == null) {
                param.hpInfo.setBedroomType(MsgConsts.ZERO_STATUS);
                param.hpInfo.setBedroomFacingType(MsgConsts.ZERO_STATUS);
            }
            param.hpInfo.setUserId(MsgConsts.UserId_Status);
            param.hpInfo.setAttentionStatus(MsgConsts.ZERO_STATUS);
            param.hpInfo.setCreatetime(System.currentTimeMillis());
            param.hpInfo.setUpdatetime(System.currentTimeMillis());
            boolean info = iHpInfoDetailService.addHpInfoDetailInfo(param.hpInfo);
            if (info==true){
                //获取最大值id
                HpInfo maxId = iHpInfoDetailService.listMaxId();
                param.hpFurniture.setUserId(MsgConsts.UserId_Status);
                param.hpRent.setUserId(MsgConsts.UserId_Status);
                param.hpRequirements.setUserId(MsgConsts.UserId_Status);
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


    @ApiOperation(httpMethod = "GET", value = "查询单个房屋详情")
    @GetMapping("getIdHpInfoDetail")
    public Result getIdHpInfoDetail(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iHpInfoDetailService.getHpInfoDetailSelectById(id,businessId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("updateHpInfoDetail")
    @ApiOperation(httpMethod = "POST", value = "更新房屋详情")
    public Result updateHpInfoDetail(@RequestBody Param param
    ){
        Result result = null;
        if (isEmpty(param.hpInfo.getId(),param.hpInfo.getBusinessId(),param.hpFurniture.getBusinessId(),param.hpRent.getBusinessId(),param.hpRequirements.getBusinessId())) {
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

    @ApiOperation(httpMethod = "GET", value = "删除房屋详情")
    @GetMapping("delHpInfoDetail")
    public Result delHpInfoDetail(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iHpRequirementsService.delHpRequirementsById(id);
            iHpRentService.delHpRentById(id);
            iHpFurnitureService.delHpFurnitureById(id);
            iHpResourcesService.delHpResourcesById(id);
            iHpInfoDetailService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "获取房屋详情小区初始化下拉框")
    @GetMapping("getHpInfoDetailCommunityName")
    public Result getHpInfoDistrictListCommunityName(@RequestParam("businessId") Long businessId){
        Result result = null;
        try {
            String listCommunityName = iHpInfoDetailService.getHpInfoDetailListCommunityName(businessId);
            JSONObject jsonObject = JSON.parseObject(listCommunityName);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,jsonObject);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


}
