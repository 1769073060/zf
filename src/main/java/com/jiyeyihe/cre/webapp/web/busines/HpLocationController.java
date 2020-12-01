package com.jiyeyihe.cre.webapp.web.busines;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.service.IHpInfoDetailService;
import com.jiyeyihe.cre.webapp.service.IHpLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "租房房产区域")
@RestController
@RequestMapping("business/houseInfo")
public class HpLocationController {

    @Resource
    private IHpLocationService iHpLocationService;



    @ApiOperation(httpMethod = "POST", value = "添加房产区域")
    @PostMapping(value = "addHpLocation")
    public Result addHpLocation(@RequestBody HpLocation hpLocation){
        Result result = null;
        Object[] objs={hpLocation.getCommunityName(), hpLocation.getBuilding(), hpLocation.getAddress(),hpLocation.getDescrip(),hpLocation.getElevator(),hpLocation.getParkingSpace(),hpLocation.getLongitude(),hpLocation.getLatitude(), hpLocation.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpLocation.setUserId(MsgConsts.UserId_Status);
            hpLocation.setCreatetime(System.currentTimeMillis());
            hpLocation.setUpdatetime(System.currentTimeMillis());
            boolean save = iHpLocationService.save(hpLocation);
            if (save==true){
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);
            }else {
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_MSG);
            }
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "删除房产区域")
    @GetMapping("delHpLocation")
    public Result delHpLocation(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iHpLocationService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateHpLocation")
    @ApiOperation(httpMethod = "POST", value = "更新房产区域数据")
    public Result updateHpLocation(@RequestBody HpLocation hpLocation){
        Result result = null;
        if (isEmpty(hpLocation.getId(),hpLocation.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpLocation.setUpdatetime(System.currentTimeMillis());
            HpLocation houseLocation = iHpLocationService.getById(hpLocation.getId());
            houseLocation = (HpLocation) ObjectConvert.combineObjectCore(hpLocation,houseLocation);
            iHpLocationService.updateById(houseLocation);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询单个房产区域详情")
    @GetMapping("getIdHpLocationDetail")
    public Result getIdHpLocationDetail(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iHpLocationService.getHpLocationSelectById(id,businessId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getHpLocationListPage")
    @ApiOperation(httpMethod = "POST", value = "房产区域分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "communityName", value = "小区名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpLocationListPage(Long pageNum,Long pageSize,String communityName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpLocationService.getHpLocationListPage(pageNum,pageSize,communityName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
