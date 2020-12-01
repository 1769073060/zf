package com.jiyeyihe.cre.webapp.web.busines;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.HpCustomerService;
import com.jiyeyihe.cre.webapp.entity.HpLocation;
import com.jiyeyihe.cre.webapp.entity.HpResources;
import com.jiyeyihe.cre.webapp.entity.HpVoiceReminder;
import com.jiyeyihe.cre.webapp.service.IHpResourcesService;
import com.jiyeyihe.cre.webapp.service.IHpVoiceReminderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "租房客户语音提醒")
@RestController
@RequestMapping("business/houseVoiceReminder")
public class HpVoiceReminderController {
    @Resource
    private IHpVoiceReminderService iHpVoiceReminderService;



    @ApiOperation(httpMethod = "POST", value = "添加客户语音提醒")
    @PostMapping(value = "addHpVoiceReminder")
    public Result addHpVoiceReminder(@RequestBody HpVoiceReminder hpVoiceReminder){
        Result result = null;
        Object[] objs={hpVoiceReminder.getVioceName(), hpVoiceReminder.getVioceStatus(),hpVoiceReminder.getVioceAuditionUrl(),hpVoiceReminder.getBusinessId(),hpVoiceReminder.getVioceName()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpVoiceReminder.setUserId(MsgConsts.UserId_Status);
            hpVoiceReminder.setCreatetime(System.currentTimeMillis());
            hpVoiceReminder.setUpdatetime(System.currentTimeMillis());
            iHpVoiceReminderService.save(hpVoiceReminder);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "删除客户语音提醒")
    @GetMapping("delHpVoiceReminder")
    public Result delHpVoiceReminder(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iHpVoiceReminderService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateHpVoiceReminder")
    @ApiOperation(httpMethod = "POST", value = "更新客户语音提醒")
    public Result updateHpVoiceReminder(@RequestBody HpVoiceReminder hpVoiceReminder){
        Result result = null;
        if (isEmpty(hpVoiceReminder.getId(),hpVoiceReminder.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpVoiceReminder.setUpdatetime(System.currentTimeMillis());
            HpVoiceReminder serviceById = iHpVoiceReminderService.getById(hpVoiceReminder.getId());
            serviceById = (HpVoiceReminder) ObjectConvert.combineObjectCore(hpVoiceReminder,serviceById);
            iHpVoiceReminderService.updateById(serviceById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询单个客户语音提醒")
    @GetMapping("getIdHpCustomerVoiceReminder")
    public Result getIdHpVoiceReminder(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            String selectListById = iHpVoiceReminderService.getHpVoiceReminderSelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getHpVoiceReminderListPage")
    @ApiOperation(httpMethod = "POST", value = "客户语音提醒分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "vioceName", value = "名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpInfoDetailListPage(Long pageNum,Long pageSize,String vioceName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpVoiceReminderService.getHpVoiceReminderListPage(pageNum,pageSize,vioceName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "POST", value = "修改客户语音状态")
    @RequestMapping(value = "updateHpVoiceReminderStatus")
    public Result updateHpVoiceReminderStatus(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            HpVoiceReminder byId = iHpVoiceReminderService.getHpVoiceReminderById(id);
            if (byId.getVioceStatus()==MsgConsts.ZERO_STATUS){
                byId.setVioceStatus(MsgConsts.FIRST_STATUS);
            }else{
                byId.setVioceStatus(MsgConsts.ZERO_STATUS);
            }
            boolean update = iHpVoiceReminderService.updateById(byId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,update);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
