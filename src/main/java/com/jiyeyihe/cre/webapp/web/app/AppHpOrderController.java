package com.jiyeyihe.cre.webapp.web.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.service.IHpCustomerViewingRecordService;
import com.jiyeyihe.cre.webapp.service.IHpOrderService;
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
@Api(tags = "app租房订单展示")
@RestController
@RequestMapping("app/appHouseOrder")
public class AppHpOrderController {

    @Resource
    private IHpOrderService iHpOrderService;
    @Resource
    private IHpCustomerViewingRecordService iHpCustomerViewingRecordService;


    @PostMapping("getAppHpOrderListPage")
    @ApiOperation(httpMethod = "POST", value = "订单列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderNo", value = "订单号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderStatus", value = "订单状态", required = true, dataType = "Integer", paramType = "query")})
    public Result getAppHpOrderListPage(Long pageNum, Long pageSize, String orderNo,Long userId,Integer orderStatus) {
        Result result = null;
        try {
            String resultData = iHpOrderService.getAppHpOrderSelectListPage(pageNum,pageSize,orderNo,userId,orderStatus);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "取消租房订单")
    @GetMapping("delHpOrder")
    public Result delHpOrder(String orderNo) {
        Result result = null;
        if (isEmpty(orderNo)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iHpOrderService.delHpOrderById(orderNo);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "查询单个租房订单详情")
    @GetMapping("getIdAppHpOrderDetail")
    public Result getIdAppHpOrderDetail(Long hpInfoId,@RequestParam("orderNo") String orderNo) {
        Result result = null;
        if (isEmpty(hpInfoId,orderNo)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iHpOrderService.getIdAppHpOrderDetail(hpInfoId, orderNo);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "租房看房记录查询单个订单详情")
    @GetMapping("getAppHpOrderById")
    public Result getAppHpOrderById(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            String selectListById = iHpCustomerViewingRecordService.getAppHpOrderById(id,userId);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


}

