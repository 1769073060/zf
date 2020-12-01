package com.jiyeyihe.cre.webapp.web.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.service.INoticeService;
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
@Api(tags = "app通知展示")
@RestController
@RequestMapping("app/appNotice")
public class AppNoticeController {

    @Resource
    private INoticeService iNoticeService;


    @PostMapping("getAppNoticeListPage")
    @ApiOperation(httpMethod = "POST", value = "app通知列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query")}
            )
    public Result getAppNoticeListPage(Long pageNum, Long pageSize) {
        Result result = null;

        try {
            String appContractSelectListPage = iNoticeService.getAppNoticeSelectListPage(pageNum, pageSize);
            JSONObject jsonObject = JSON.parseObject(appContractSelectListPage);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "app通知信息")
    @GetMapping("getIdAppNotice")
    public Result getIdAppNotice(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iNoticeService.getAppNoticeSelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


}

