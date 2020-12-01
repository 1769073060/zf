package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.service.IHpAppointmentHouseService;
import com.jiyeyihe.cre.webapp.service.IHpCustomerViewingRecordService;
import com.jiyeyihe.cre.webapp.service.IHpFollowHouseService;
import com.jiyeyihe.cre.webapp.service.IHpInfoDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app客户看房记录")
@RestController
@RequestMapping("app/appHouseCustomerViewingRecord")
public class AppHpCustomerViewingRecordController {

    @Resource
    private IHpCustomerViewingRecordService iHpCustomerViewingRecordService;
    @Resource
    private IHpInfoDetailService iHpInfoDetailService;
    @Resource
    private IHpAppointmentHouseService iHpAppointmentHouseService;
    @Resource
    private IHpFollowHouseService iHpFollowHouseService;

    @ApiOperation(httpMethod = "POST", value = "租房添加约看房时间")
    @PostMapping(value = "addAppHpCustomerViewingRecord")
    public Result addAppHpCustomerViewingRecord(@RequestBody HpCustomerViewingRecord hpCustomerViewingRecord){
        Result result = null;
        Object[] objs={hpCustomerViewingRecord.getCustomerName(),hpCustomerViewingRecord.getContactNumber(),hpCustomerViewingRecord.getLivingPopulation(),hpCustomerViewingRecord.getUserId(),hpCustomerViewingRecord.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String a = hpCustomerViewingRecord.getWeekTime();
            String b = " ";
            String c = hpCustomerViewingRecord.getHourTime();
            String d = ":00:00";
            String time = a+b+c+d;
            Date date = simpleDateFormat.parse(time);
            long viewTime = date.getTime();
            hpCustomerViewingRecord.setPaymentStatus(MsgConsts.ZERO_STATUS);
            hpCustomerViewingRecord.setViewingStatus(MsgConsts.ZERO_STATUS);
            hpCustomerViewingRecord.setViewHouseTime(viewTime);
            hpCustomerViewingRecord.setCreatetime(System.currentTimeMillis());
            hpCustomerViewingRecord.setUpdatetime(System.currentTimeMillis());
            boolean save = iHpCustomerViewingRecordService.save(hpCustomerViewingRecord);
            //如果发布成功，就把约房状态修改为1
            if (save==true){
                HpAppointmentHouse appointmentHouse = new HpAppointmentHouse();
                UpdateWrapper updateWrapper = new UpdateWrapper<>();
                //约看房表
                appointmentHouse.setHpInfoId(hpCustomerViewingRecord.getHpInfoId());
                appointmentHouse.setUserId(hpCustomerViewingRecord.getUserId());
                appointmentHouse.setAppointmentStatus(MsgConsts.FIRST_STATUS);
                updateWrapper.eq("hp_info_id",appointmentHouse.getHpInfoId());
                updateWrapper.eq("user_id",appointmentHouse.getUserId());
                iHpAppointmentHouseService.update(appointmentHouse, updateWrapper);
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


    @PostMapping("getAppHpCustomerViewingRecordSelectListPage")
    @ApiOperation(httpMethod = "POST", value = "租房看房记录列表展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "viewingStatus", value = "看房状态", required = true, dataType = "Integer", paramType = "query"),
           })
    public Result getAppHpCustomerViewingRecordWaitingSelectListPage(Long pageNum, Long pageSize,Long userId,Integer viewingStatus) {
        Result result = null;
        if (isEmpty(userId,viewingStatus)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpCustomerViewingRecordService.getAppHpCustomerViewingRecordSelectListPage(pageNum,pageSize,userId,viewingStatus);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "租房看房记录查询单个房源详情")
    @GetMapping("getIdAppHpCustomerViewingRecord")
    public Result getIdAppHpInfoDetail(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            HpFollowHouse followHouse = new HpFollowHouse();
            HpAppointmentHouse appointmentHouse = new HpAppointmentHouse();
            QueryWrapper queryWrapper = new QueryWrapper();
            //查询关注表和看房记录有没有这个用户操作过
            HpFollowHouse followById = iHpInfoDetailService.getAppHpResourceFollowStatusById(id, userId);
            queryWrapper.eq("hp_info_id",id);
            queryWrapper.eq("user_id",userId);
            HpAppointmentHouse appointmentById = iHpAppointmentHouseService.getOne(queryWrapper);
            //查询当前进入的房屋id
            HpInfo infoById = iHpInfoDetailService.getAppHpResourceAttentionById(id);
            if (followById==null){//因为用户进来会显示是否关注
                followHouse.setUserId(userId);
                followHouse.setAttentionStatus(MsgConsts.ZERO_STATUS);
                followHouse.setHpInfoId(infoById.getId());
                iHpFollowHouseService.save(followHouse);
            }if (appointmentById==null){//因为用户进来会显示是否有看过房
                appointmentHouse.setUserId(userId);
                appointmentHouse.setAppointmentStatus(MsgConsts.ZERO_STATUS);
                appointmentHouse.setHpInfoId(infoById.getId());
                appointmentHouse.setCreatetime(System.currentTimeMillis());
                appointmentHouse.setUpdatetime(System.currentTimeMillis());
                iHpAppointmentHouseService.save(appointmentHouse);
            }
            String selectListById = iHpCustomerViewingRecordService.getAppHpCustomerViewingRecordById(id,userId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }




    @ApiOperation(httpMethod = "POST", value = "删除租房看房记录")
    @RequestMapping(value = "delAppHpCustomerViewingRecord")
    public Result delAppHpCustomerViewingRecord(@RequestParam(value = "hpCustomerViewingRecordId",required=true)  Long hpCustomerViewingRecordId,
                                                @RequestParam(value = "hpAppointmentHouseId",required=true) Long hpAppointmentHouseId) {
        Result result = null;
        if (isEmpty(hpCustomerViewingRecordId,hpAppointmentHouseId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            int record = iHpCustomerViewingRecordService.delAppHpCustomerViewingRecord(hpCustomerViewingRecordId, hpAppointmentHouseId);
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

    @ApiOperation(httpMethod = "POST", value = "修改app租房看房记录状态")
    @RequestMapping(value = "updateAppHpCustomerViewingRecordStatus")
    public Result updateAppHpCustomerViewingRecordStatus(Long hpCustomerViewingRecordId,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(hpCustomerViewingRecordId,businessId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            HpCustomerViewingRecord byId = iHpCustomerViewingRecordService.getByIdAppHpCustomerViewingRecord(hpCustomerViewingRecordId);
            if (byId.getViewingStatus()==MsgConsts.ZERO_STATUS){
                byId.setViewingStatus(MsgConsts.FIRST_STATUS);
            }
            iHpCustomerViewingRecordService.updateById(byId);
            HpCustomerViewingRecord selectById = iHpCustomerViewingRecordService.getByIdAppHpCustomerViewingRecord(hpCustomerViewingRecordId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,selectById);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


}
