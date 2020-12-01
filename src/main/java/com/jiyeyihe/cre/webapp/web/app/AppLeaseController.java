package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.mapper.HpOrderMapper;
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
@Api(tags = "app根据用戶id显示是否有租房写字楼数据")
@RestController
@RequestMapping("app/appLease")
public class AppLeaseController {

    @Resource
    private IHpOrderService iHpOrderService;
    @Resource
    private IObOrderService iObOrderService;
    @Resource
    private IHpCustomerViewingRecordService iHpCustomerViewingRecordService;
    @Resource
    private IObCustomerViewingRecordService iObCustomerViewingRecordService;
    @Resource
    private IHpFollowHouseService iHpFollowHouseService;
    @Resource
    private IObFollowHouseService iObFollowHouseService;


    @PostMapping("getAppOrder")
    @ApiOperation(httpMethod = "POST", value = "订单展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
    })
    public Result getAppOrder(Long userId) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            int userHpOrder = iHpOrderService.getByIdHpUserOrder(userId);
            int userObOrder = iObOrderService.getByIdObUserOrder(userId);
            log.info("userHpOrder："+userHpOrder);
            log.info("userObOrder："+userObOrder);

            if (userHpOrder==MsgConsts.FIRST_STATUS&&userObOrder==MsgConsts.ZERO_STATUS){
                //如果HpOrder查询有，ObOrder查询没有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.ZERO_STATUS);
            }
            if (userHpOrder==MsgConsts.ZERO_STATUS&&userObOrder==MsgConsts.FIRST_STATUS){
                //如果HpOrder查询没有，ObOrder查询有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FIRST_STATUS);
            }
            if (userHpOrder==MsgConsts.FIRST_STATUS&&userObOrder==MsgConsts.FIRST_STATUS){
                //如果HpOrder查询有，ObOrder查询有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SECOND_STATUS);
            }
            if (userHpOrder==MsgConsts.ZERO_STATUS&&userObOrder==MsgConsts.ZERO_STATUS){
                //如果HpOrder查询没有，ObOrder查询没有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.THIRD_STATUS);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getAppCustomerViewingRecord")
    @ApiOperation(httpMethod = "POST", value = "看房展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
    })
    public Result getAppCustomerViewingRecord(Long userId) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            int userHpCustomerViewingRecord = iHpCustomerViewingRecordService.getByIdHpUserCustomerViewingRecord(userId);
            int userObCustomerViewingRecord = iObCustomerViewingRecordService.getByIdObUserCustomerViewingRecord(userId);
            log.info("userHpOrder："+userHpCustomerViewingRecord);
            log.info("userObOrder："+userObCustomerViewingRecord);

            if (userHpCustomerViewingRecord==MsgConsts.FIRST_STATUS&&userObCustomerViewingRecord==MsgConsts.ZERO_STATUS){
                //如果HpOrder查询有，ObOrder查询没有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.ZERO_STATUS);
            }
            if (userHpCustomerViewingRecord==MsgConsts.ZERO_STATUS&&userObCustomerViewingRecord==MsgConsts.FIRST_STATUS){
                //如果HpOrder查询没有，ObOrder查询有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FIRST_STATUS);
            }
            if (userHpCustomerViewingRecord==MsgConsts.FIRST_STATUS&&userObCustomerViewingRecord==MsgConsts.FIRST_STATUS){
                //如果HpOrder查询有，ObOrder查询有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SECOND_STATUS);
            }
            if (userHpCustomerViewingRecord==MsgConsts.ZERO_STATUS&&userObCustomerViewingRecord==MsgConsts.ZERO_STATUS){
                //如果HpOrder查询没有，ObOrder查询没有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.THIRD_STATUS);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getAppAttention")
    @ApiOperation(httpMethod = "POST", value = "关注展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
    })
    public Result getAppAttention(Long userId) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            int userHpOrder = iHpFollowHouseService.getByIdHpUserFollow(userId);
            int userObOrder = iObFollowHouseService.getByIdObUserFollow(userId);
            log.info("userHpOrder："+userHpOrder);
            log.info("userObOrder："+userObOrder);

            if (userHpOrder==MsgConsts.FIRST_STATUS&&userObOrder==MsgConsts.ZERO_STATUS){
                //如果HpOrder查询有，ObOrder查询没有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.ZERO_STATUS);
            }
            if (userHpOrder==MsgConsts.ZERO_STATUS&&userObOrder==MsgConsts.FIRST_STATUS){
                //如果HpOrder查询没有，ObOrder查询有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FIRST_STATUS);
            }
            if (userHpOrder==MsgConsts.FIRST_STATUS&&userObOrder==MsgConsts.FIRST_STATUS){
                //如果HpOrder查询有，ObOrder查询有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SECOND_STATUS);
            }
            if (userHpOrder==MsgConsts.ZERO_STATUS&&userObOrder==MsgConsts.ZERO_STATUS){
                //如果HpOrder查询没有，ObOrder查询没有
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.THIRD_STATUS);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
