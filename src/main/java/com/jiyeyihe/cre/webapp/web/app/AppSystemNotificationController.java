package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.ObFollowHouse;
import com.jiyeyihe.cre.webapp.entity.ObInfo;
import com.jiyeyihe.cre.webapp.service.IObOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app系统通知")
@RestController
@RequestMapping("app/appSystemNotification")
public class AppSystemNotificationController {

    @Resource
    private IObOrderService iObOrderService;


    @ApiOperation(httpMethod = "GET", value = "查询单个系统通知")
    @GetMapping("getIdAppSystemNotification")
    public Result getIdAppObInfoDetail(Long userId,Integer type) {
        Result result = null;
        if (isEmpty(userId,type)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if (type==MsgConsts.ZERO_STATUS){
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,false);
            }else {
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,true);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getAppSystemNotificationListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼订单列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
        })
    public Result getAppSystemNotificationListPage(Long pageNum, Long pageSize, String orderNo,Long userId,Integer orderStatus) {
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

}
