package com.jiyeyihe.cre.webapp.web.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpOrder;
import com.jiyeyihe.cre.webapp.entity.ObFollowHouse;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.entity.ObOrder;
import com.jiyeyihe.cre.webapp.service.IHpCustomerViewingRecordService;
import com.jiyeyihe.cre.webapp.service.IHpOrderService;
import com.jiyeyihe.cre.webapp.service.IObCustomerViewingRecordService;
import com.jiyeyihe.cre.webapp.service.IObOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "app写字楼订单展示")
@RestController
@RequestMapping("app/appOfficeBuildOrder")
public class AppObOrderController {

    @Resource
    private IObOrderService iObOrderService;
    @Resource
    private IObCustomerViewingRecordService iObCustomerViewingRecordService;

    @PostMapping("getAppObOrderListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼订单列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderNo", value = "订单号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderStatus", value = "订单状态", required = true, dataType = "Integer", paramType = "query")})
    public Result getAppObOrderListPage(Long pageNum, Long pageSize, String orderNo,Long userId,Integer orderStatus) {
        Result result = null;
        try {
            String resultData = iObOrderService.getAppObOrderSelectListPage(pageNum,pageSize,orderNo,userId,orderStatus);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "取消写字楼订单")
    @GetMapping("delObOrder")
    public Result delObOrder(String orderNo) {
        Result result = null;
        if (isEmpty(orderNo)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iObOrderService.delObOrderById(orderNo);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "查询单个写字楼订单详情")
    @GetMapping("getIdAppObOrderDetail")
    public Result getIdAppObOrderDetail(Long obInfoId,@RequestParam("orderNo") String orderNo) {
        Result result = null;
        if (isEmpty(obInfoId,orderNo)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iObOrderService.getIdAppObOrderDetail(obInfoId, orderNo);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "写字楼查询单个订单详情")
    @GetMapping("getAppObOrderById")
    public Result getAppObOrderById(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            String selectListById = iObCustomerViewingRecordService.getAppObOrderById(id,userId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}

