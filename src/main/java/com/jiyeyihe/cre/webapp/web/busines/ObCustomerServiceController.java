package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.ObCustomerService;
import com.jiyeyihe.cre.webapp.service.IObCustomerServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "写字楼房产客服")
@RestController
@RequestMapping("business/officeBuildingHouseCustomerService")
public class ObCustomerServiceController {

    @Resource
    private IObCustomerServiceService iObCustomerService;


    @PostMapping("getObCustomerService")
    @ApiOperation(httpMethod = "POST", value = "获取写字楼房产客服房屋名称下拉框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "propertyName", value = "楼盘名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObCustomerService(Long pageNum, Long pageSize, String propertyName, Long businessId){
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectList = iObCustomerService.getObCustomerSelectList(pageNum, pageSize, propertyName,businessId);
            JSONObject jsonObject = JSON.parseObject(selectList);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,jsonObject);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @PostMapping("getObCustomerServiceListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼房产客服分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "houseName", value = "房屋名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObCustomerServiceListPage(Long pageNum,Long pageSize,String houseName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObCustomerService.getObCustomerListPage(pageNum,pageSize,houseName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "添加写字楼房产客服")
    @PostMapping(value = "addObCustomerService")
    public Result addObCustomerService(@RequestBody ObCustomerService obCustomerService){
        Result result = null;
        Object[] objs={obCustomerService.getObInfoId(),obCustomerService.getOfficeName(), obCustomerService.getConsumerServicePhone(), obCustomerService.getConsumerServiceWechat(), obCustomerService.getConsumerServiceQq(),obCustomerService.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obCustomerService.setUserId(MsgConsts.UserId_Status);
            obCustomerService.setObInfoId(obCustomerService.getObInfoId());
            obCustomerService.setCreatetime(System.currentTimeMillis());
            obCustomerService.setUpdatetime(System.currentTimeMillis());
            iObCustomerService.save(obCustomerService);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }


    @PostMapping("updateObCustomerService")
    @ApiOperation(httpMethod = "POST", value = "更新写字楼房产客服数据")
    public Result updateObCustomerService(@RequestBody ObCustomerService obCustomerService){
        Result result = null;
        if (isEmpty(obCustomerService.getId(),obCustomerService.getObInfoId(),obCustomerService.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            obCustomerService.setUpdatetime(System.currentTimeMillis());
            updateWrapper.eq("id",obCustomerService.getId());
            iObCustomerService.update(obCustomerService, updateWrapper);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "删除写字楼房产客服")
    @GetMapping("delObCustomerService")
    public Result delObCustomerService(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iObCustomerService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询单个写字楼房产客服")
    @GetMapping("getIdObCustomerService")
    public Result getIdObCustomerService(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iObCustomerService.getObCustomersSelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}

