package com.jiyeyihe.cre.webapp.web.busines;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.HpCustomerService;
import com.jiyeyihe.cre.webapp.service.IHpCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "租房房产客服")
@RestController
@RequestMapping("business/houseCustomerService")
public class HpCustomerServiceController {

    @Resource
    private IHpCustomerService iHpCustomerService;


    @PostMapping("getHpCustomerService")
    @ApiOperation(httpMethod = "POST", value = "获取房产客服房屋名称下拉框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "communityName", value = "小区名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpCustomerService(Long pageNum, Long pageSize, String communityName,Long businessId){
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectList = iHpCustomerService.getHpCustomerSelectList(pageNum, pageSize, communityName,businessId);
            JSONObject jsonObject = JSON.parseObject(selectList);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,jsonObject);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getHpCustomerServiceListPage")
    @ApiOperation(httpMethod = "POST", value = "房产客服分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "houseName", value = "房屋名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpCustomerServiceListPage(Long pageNum,Long pageSize,String houseName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpCustomerService.getHpCustomerListPage(pageNum,pageSize,houseName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "POST", value = "添加房产客服")
    @PostMapping(value = "addHpCustomerService")
    public Result addHpCustomerService(@RequestBody HpCustomerService hpCustomerService){
        Result result = null;
        Object[] objs={hpCustomerService.getHpInfoId(),hpCustomerService.getHouseName(), hpCustomerService.getConsumerServicePhone(), hpCustomerService.getConsumerServiceWechat(), hpCustomerService.getConsumerServiceQq(),hpCustomerService.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpCustomerService.setUserId(MsgConsts.UserId_Status);
            hpCustomerService.setHpInfoId(hpCustomerService.getHpInfoId());
            hpCustomerService.setCreatetime(System.currentTimeMillis());
            hpCustomerService.setUpdatetime(System.currentTimeMillis());
            iHpCustomerService.save(hpCustomerService);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }


    @PostMapping("updateHpCustomerService")
    @ApiOperation(httpMethod = "POST", value = "更新房产客服数据")
    public Result updateHpCustomerService(@RequestBody HpCustomerService hpCustomerService){
        Result result = null;
        if (isEmpty(hpCustomerService.getHpInfoId(),hpCustomerService.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            hpCustomerService.setUpdatetime(System.currentTimeMillis());
            updateWrapper.eq("id",hpCustomerService.getId());
            iHpCustomerService.update(hpCustomerService, updateWrapper);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "删除房产客服")
    @GetMapping("delHpCustomerService")
    public Result delHpCustomerService(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iHpCustomerService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "查询单个房产客服配置")
    @GetMapping("getIdHpCustomerService")
    public Result getIdHpCustomerService(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iHpCustomerService.getHpCustomersSelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
