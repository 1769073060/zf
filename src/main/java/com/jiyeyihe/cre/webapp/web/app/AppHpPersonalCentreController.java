package com.jiyeyihe.cre.webapp.web.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.service.IHpInfoDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Slf4j
@Api(tags = "app租房我的关注")
@RestController
@RequestMapping("app/appHousePersonalCentre")
public class AppHpPersonalCentreController {

    @Resource
    private IHpInfoDetailService iHpInfoDetailService;

    @PostMapping("getAppHpPersonalCentreListPage")
    @ApiOperation(httpMethod = "POST", value = "我关注的房源列表展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userLongitude", value = "用户经度", required = true, dataType = "Double", paramType = "query"),
            @ApiImplicitParam(name = "userLatitude", value = "用户纬度", required = true, dataType = "Double", paramType = "query")})
    public Result getAppHpPersonalCentreListPage(Long pageNum, Long pageSize,Long userId,Double userLongitude, Double userLatitude) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpInfoDetailService.getAppHpPersonalCentreListPage(pageNum,pageSize,userId,userLongitude,userLatitude);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



}
