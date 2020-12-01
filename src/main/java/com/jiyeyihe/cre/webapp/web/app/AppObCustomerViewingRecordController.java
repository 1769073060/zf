package com.jiyeyihe.cre.webapp.web.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app写字楼客户看房记录")
@RestController
@RequestMapping("app/appOfficeBuildingCustomerViewingRecord")
public class AppObCustomerViewingRecordController {

    @Resource
    private IObCustomerViewingRecordService iObCustomerViewingRecordService;
    @Resource
    private IObInfoDetailService iObInfoDetailService;
    @Resource
    private IObAppointmentHouseService iObAppointmentHouseService;
    @Resource
    private IObFollowHouseService iObFollowHouseService;

    @ApiOperation(httpMethod = "POST", value = "写字楼添加约看房时间")
    @PostMapping(value = "addAppObCustomerViewingRecord")
    public Result addAppObCustomerViewingRecord(@RequestBody ObCustomerViewingRecord obCustomerViewingRecord){
        Result result = null;
        Object[] objs={obCustomerViewingRecord.getCustomerName(),obCustomerViewingRecord.getContactNumber(),obCustomerViewingRecord.getLivingPopulation(),obCustomerViewingRecord.getUserId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
//            ObCustomerViewingRecord.setBusinessId(MsgConsts.BusinessId_Status);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String a = obCustomerViewingRecord.getWeekTime();
            String b = " ";
            String c = obCustomerViewingRecord.getHourTime();
            String d = ":00:00";
            String time = a+b+c+d;
            Date date = simpleDateFormat.parse(time);
            long viewTime = date.getTime();
            obCustomerViewingRecord.setViewingStatus(MsgConsts.ZERO_STATUS);
            obCustomerViewingRecord.setPaymentStatus(MsgConsts.ZERO_STATUS);
            obCustomerViewingRecord.setViewHouseTime(viewTime);
            obCustomerViewingRecord.setCreatetime(System.currentTimeMillis());
            obCustomerViewingRecord.setUpdatetime(System.currentTimeMillis());
            boolean save = iObCustomerViewingRecordService.save(obCustomerViewingRecord);
            //如果发布成功，就把约房状态修改为1
            if (save==true){
                ObAppointmentHouse appointmentHouse = new ObAppointmentHouse();
                UpdateWrapper updateWrapper = new UpdateWrapper<>();
                //约看房表
                appointmentHouse.setObInfoId(obCustomerViewingRecord.getObInfoId());
                appointmentHouse.setUserId(obCustomerViewingRecord.getUserId());
                appointmentHouse.setAppointmentStatus(MsgConsts.FIRST_STATUS);
                updateWrapper.eq("ob_info_id",appointmentHouse.getObInfoId());
                updateWrapper.eq("user_id",appointmentHouse.getUserId());
                iObAppointmentHouseService.update(appointmentHouse, updateWrapper);
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);
            }else{
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_MSG);
            }
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }




    @PostMapping("getAppObCustomerViewingRecordSelectListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼看房记录列表展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "viewingStatus", value = "看房状态", required = true, dataType = "Integer", paramType = "query"),
        })
    public Result getAppObCustomerViewingRecordWaitingSelectListPage(Long pageNum, Long pageSize,Long userId,Integer viewingStatus) {
        Result result = null;
        if (isEmpty(userId,viewingStatus)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObCustomerViewingRecordService.getAppObCustomerViewingRecordSelectListPage(pageNum,pageSize,userId,viewingStatus);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "写字楼看房记录查询单个房源详情")
    @GetMapping("getIdAppObCustomerViewingRecord")
    public Result getIdAppObInfoDetail(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            ObFollowHouse followHouse = new ObFollowHouse();
            ObAppointmentHouse appointmentHouse = new ObAppointmentHouse();
            QueryWrapper queryWrapper = new QueryWrapper();
            //查询关注表和看房记录有没有这个用户操作过
            ObFollowHouse followById = iObInfoDetailService.getAppObResourceFollowStatusById(id, userId);
            queryWrapper.eq("ob_info_id",id);
            queryWrapper.eq("user_id",userId);
            ObAppointmentHouse appointmentById = iObAppointmentHouseService.getOne(queryWrapper);
            //查询当前进入的房屋id
            ObInfo infoById = iObInfoDetailService.getAppObResourceAttentionById(id);
            if (followById==null){//因为用户进来会显示是否关注
                followHouse.setUserId(userId);
                followHouse.setAttentionStatus(MsgConsts.ZERO_STATUS);
                followHouse.setObInfoId(infoById.getId());
                iObFollowHouseService.save(followHouse);
            }if (appointmentById==null){//因为用户进来会显示是否有看过房
                appointmentHouse.setUserId(userId);
                appointmentHouse.setAppointmentStatus(MsgConsts.ZERO_STATUS);
                appointmentHouse.setObInfoId(infoById.getId());
                appointmentHouse.setCreatetime(System.currentTimeMillis());
                appointmentHouse.setUpdatetime(System.currentTimeMillis());
                iObAppointmentHouseService.save(appointmentHouse);
            }
            String selectListById = iObCustomerViewingRecordService.getAppObCustomerViewingRecordById(id,userId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "POST", value = "删除写字楼看房记录")
    @RequestMapping(value = "delAppObCustomerViewingRecord")
    public Result delAppObCustomerViewingRecord(@RequestParam(value = "obCustomerViewingRecordId",required=true)  Long obCustomerViewingRecordId,
                                                @RequestParam(value = "obAppointmentHouseId",required=true) Long obAppointmentHouseId) {
        Result result = null;
        if (isEmpty(obCustomerViewingRecordId,obAppointmentHouseId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            int record = iObCustomerViewingRecordService.delAppObCustomerViewingRecord(obCustomerViewingRecordId, obAppointmentHouseId);
            if (record>MsgConsts.ZERO_STATUS){
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);
            }else{
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_MSG);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "修改app写字楼看房记录状态")
    @RequestMapping(value = "updateAppObCustomerViewingRecordStatus")
    public Result updateAppObCustomerViewingRecordStatus(Long obCustomerViewingRecordId,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(obCustomerViewingRecordId,businessId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            ObCustomerViewingRecord byId = iObCustomerViewingRecordService.getByIdAppObCustomerViewingRecord(obCustomerViewingRecordId);
            if (byId.getViewingStatus()==MsgConsts.ZERO_STATUS){
                byId.setViewingStatus(MsgConsts.FIRST_STATUS);
            }
            iObCustomerViewingRecordService.updateById(byId);
            ObCustomerViewingRecord selectById = iObCustomerViewingRecordService.getByIdAppObCustomerViewingRecord(obCustomerViewingRecordId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,selectById);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
