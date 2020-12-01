package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.ObLocation;
import com.jiyeyihe.cre.webapp.service.IObLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "写字楼区域")
@RestController
@RequestMapping("business/officeBuildingLocation")
public class ObLocationController {

    @Resource
    private IObLocationService iObLocationService;


    @ApiOperation(httpMethod = "POST", value = "添加写字楼区域")
    @PostMapping(value = "addObLocation")
    public Result addObLocation(@RequestBody ObLocation obLocation){
        Result result = null;
        Object[] objs={obLocation.getPropertyName(), obLocation.getDistrict(), obLocation.getAddress(),obLocation.getOfficeDescrip(),obLocation.getLongitude(),obLocation.getLatitude(),obLocation.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obLocation.setUserId(MsgConsts.UserId_Status);
            obLocation.setCreatetime(System.currentTimeMillis());
            obLocation.setUpdatetime(System.currentTimeMillis());
            boolean save = iObLocationService.save(obLocation);
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

    @ApiOperation(httpMethod = "GET", value = "删除写字楼区域")
    @GetMapping("delObLocation")
    public Result delObLocation(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iObLocationService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_CODE);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateObLocation")
    @ApiOperation(httpMethod = "POST", value = "更新写字楼区域数据")
    public Result updateObLocation(@RequestBody ObLocation obLocation){
        Result result = null;
        if (isEmpty(obLocation.getId(),obLocation.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obLocation.setUpdatetime(System.currentTimeMillis());
            ObLocation houseLocation = iObLocationService.getById(obLocation.getId());
            houseLocation = (ObLocation) ObjectConvert.combineObjectCore(obLocation,houseLocation);
            iObLocationService.updateById(houseLocation);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询单个写字楼区域详情")
    @GetMapping("getIdObLocationDetail")
    public Result getIdObLocationDetail(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iObLocationService.getObLocationSelectById(id,businessId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getObLocationListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼区域分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "propertyName", value = "楼盘名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObLocationListPage(Long pageNum,Long pageSize,String propertyName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObLocationService.getObLocationListPage(pageNum,pageSize,propertyName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}

