package com.jiyeyihe.cre.webapp.web.busines;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObParam;
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
@Api(tags = "写字楼详情")
@RestController
@RequestMapping("business/officeBuildingInfoDetail")
public class ObInfoDetailController {
    @Resource
    private IObInfoDetailService iObInfoDetailService;
    @Resource
    private IObRentService iObRentService;
    @Resource
    private IObMatchingService iObMatchingService;
    @Resource
    private IObSurroundingFacilitiesService iObSurroundingFacilitiesService;
    @Resource
    private IObResourcesService iObResourcesService;


    @PostMapping("getObInfoDetailListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼详情分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "propertyName", value = "楼盘名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObInfoDetailListPage(Long pageNum, Long pageSize, String propertyName, Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObInfoDetailService.getObInfoDetailListPage(pageNum,pageSize,propertyName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "添加写字楼详情")
    @PostMapping(value = "addObInfoDetail")
    public Result addObInfoDetail(@RequestBody ObParam obParam
    ){
        Result result = null;
        Object[] objs={obParam.obInfo.getBusinessId(),obParam.obMatching.getBusinessId(),obParam.obRent.getBusinessId(),obParam.obSurroundingFacilities.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            obParam.obInfo.setUserId(MsgConsts.UserId_Status);
            obParam.obInfo.setCreatetime(System.currentTimeMillis());
            obParam.obInfo.setUpdatetime(System.currentTimeMillis());
            boolean info = iObInfoDetailService.addObInfoDetailInfo(obParam.obInfo);
            if (info==true){
                //获取最大值id
                ObInfo maxId = iObInfoDetailService.listMaxId();
                obParam.obMatching.setUserId(MsgConsts.UserId_Status);
                obParam.obRent.setUserId(MsgConsts.UserId_Status);
                obParam.obSurroundingFacilities.setUserId(MsgConsts.UserId_Status);
                obParam.obMatching.setObInfoId(maxId.getId());
                obParam.obRent.setObInfoId(maxId.getId());
                obParam.obSurroundingFacilities.setObInfoId(maxId.getId());
                obParam.obMatching.setCreatetime(System.currentTimeMillis());
                obParam.obMatching.setUpdatetime(System.currentTimeMillis());
                obParam.obRent.setCreatetime(System.currentTimeMillis());
                obParam.obRent.setUpdatetime(System.currentTimeMillis());
                obParam.obSurroundingFacilities.setCreatetime(System.currentTimeMillis());
                obParam.obSurroundingFacilities.setUpdatetime(System.currentTimeMillis());
                String infoDetail = iObInfoDetailService.addObInfoDetail(obParam.obMatching, obParam.obRent, obParam.obSurroundingFacilities);
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


    @ApiOperation(httpMethod = "GET", value = "查询单个写字楼详情")
    @GetMapping("getIdObInfoDetail")
    public Result getIdObInfoDetail(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iObInfoDetailService.getObInfoDetailSelectById(id,businessId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("updateObInfoDetail")
    @ApiOperation(httpMethod = "POST", value = "更新写字楼详情")
    public Result updateObInfoDetail(@RequestBody ObParam obParam
    ){
        Result result = null;
        if (isEmpty(obParam.obInfo.getId(),
                obParam.obInfo.getBusinessId(),
                obParam.obMatching.getBusinessId(),
                obParam.obRent.getBusinessId(),
                obParam.obSurroundingFacilities.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obParam.obInfo.setUpdatetime(System.currentTimeMillis());
            obParam.obMatching.setUpdatetime(System.currentTimeMillis());
            obParam.obRent.setUpdatetime(System.currentTimeMillis());
            obParam.obSurroundingFacilities.setUpdatetime(System.currentTimeMillis());
            ObInfo ObInfo = iObInfoDetailService.getById(obParam.obInfo.getId());
            ObInfo = (ObInfo) ObjectConvert.combineObjectCore(obParam.obInfo,ObInfo);
            iObInfoDetailService.updateById(ObInfo);
            iObMatchingService.updateObInfoDetailMatchingByLocationId(obParam.obInfo,obParam.obMatching);
            iObRentService.updateObInfoDetailRentByLocationId(obParam.obInfo,obParam.obRent);
            iObSurroundingFacilitiesService.updateObInfoDetailSurroundingFacilitiesByLocationId(obParam.obInfo,obParam.obSurroundingFacilities);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "删除写字楼详情")
    @GetMapping("delObInfoDetail")
    public Result delObInfoDetail(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iObMatchingService.delObMatchingById(id);
            iObRentService.delObRentById(id);
            iObSurroundingFacilitiesService.delObSurroundingFacilitiesById(id);
            iObResourcesService.delObResourcesById(id);
            iObInfoDetailService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "获取写字楼楼盘初始化下拉框")
    @GetMapping("getObInfoDetailPropertyName")
    public Result getObInfoDetailListPropertyName(@RequestParam("businessId") Long businessId){
        Result result = null;
        try {
            String listCommunityName = iObInfoDetailService.getObInfoDetailListPropertyName(businessId);
            JSONObject jsonObject = JSON.parseObject(listCommunityName);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,jsonObject);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


}
