package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.ObVoiceReminder;
import com.jiyeyihe.cre.webapp.service.IObVoiceReminderService;
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
@Api(tags = "写字楼客户语音提醒")
@RestController
@RequestMapping("business/officeBuildingHouseVoiceReminder")
public class ObVoiceReminderController {
    @Resource
    private IObVoiceReminderService iObVoiceReminderService;



    @ApiOperation(httpMethod = "POST", value = "添加写字楼客户语音提醒")
    @PostMapping(value = "addObVoiceReminder")
    public Result addObVoiceReminder(@RequestBody ObVoiceReminder obVoiceReminder){
        Result result = null;
        Object[] objs={obVoiceReminder.getVioceName(), obVoiceReminder.getVioceStatus(),obVoiceReminder.getVioceAuditionUrl(),obVoiceReminder.getBusinessId(),obVoiceReminder.getVioceName()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obVoiceReminder.setUserId(MsgConsts.UserId_Status);
            obVoiceReminder.setCreatetime(System.currentTimeMillis());
            obVoiceReminder.setUpdatetime(System.currentTimeMillis());
            iObVoiceReminderService.save(obVoiceReminder);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "删除写字楼客户语音提醒")
    @GetMapping("delObVoiceReminder")
    public Result delObVoiceReminder(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iObVoiceReminderService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateObVoiceReminder")
    @ApiOperation(httpMethod = "POST", value = "更新写字楼客户语音提醒")
    public Result updateObVoiceReminder(@RequestBody ObVoiceReminder obVoiceReminder){
        Result result = null;
        if (isEmpty(obVoiceReminder.getId(),obVoiceReminder.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obVoiceReminder.setUpdatetime(System.currentTimeMillis());
            ObVoiceReminder serviceById = iObVoiceReminderService.getById(obVoiceReminder.getId());
            serviceById = (ObVoiceReminder) ObjectConvert.combineObjectCore(obVoiceReminder,serviceById);
            iObVoiceReminderService.updateById(serviceById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询写字楼单个客户语音提醒")
    @GetMapping("getIdObCustomerVoiceReminder")
    public Result getIdObVoiceReminder(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            String selectListById = iObVoiceReminderService.getObVoiceReminderSelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getObVoiceReminderListPage")
    @ApiOperation(httpMethod = "POST", value = "客户语音提醒分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "vioceName", value = "名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObVoiceReminderListPage(Long pageNum,Long pageSize,String vioceName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObVoiceReminderService.getObVoiceReminderListPage(pageNum,pageSize,vioceName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "POST", value = "修改客户语音状态")
    @RequestMapping(value = "updateObVoiceReminderStatus")
    public Result updateObVoiceReminderStatus(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            ObVoiceReminder byId = iObVoiceReminderService.getObVoiceReminderById(id);
            if (byId.getVioceStatus()==MsgConsts.ZERO_STATUS){
                byId.setVioceStatus(MsgConsts.FIRST_STATUS);
            }else{
                byId.setVioceStatus(MsgConsts.ZERO_STATUS);
            }
            boolean update = iObVoiceReminderService.updateById(byId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,update);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}

