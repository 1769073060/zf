package com.jiyeyihe.cre.webapp.web.ucm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.service.IHpApplyService;
import com.jiyeyihe.cre.webapp.service.IObApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@Api(tags = "平台端显示用户通过的申请信息")
@RestController
@RequestMapping("ucm/apply")
public class ApplyController {

    @Resource
    private IHpApplyService iHpApplyService;
    @Resource
    private IObApplyService iObApplyService;


    @PostMapping("getHpApplySelectListPage")
    @ApiOperation(httpMethod = "POST", value = "租房 用户通过的申请信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
    })
    public Result getHpApplySelectListPage(Long pageNum, Long pageSize) {
        Result result = null;
        try {
            String resultData = iHpApplyService.getHpApplySelectListPage(pageNum,pageSize);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getObApplySelectListPage")
    @ApiOperation(httpMethod = "POST", value = "写字楼 用户通过的申请信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
          })
    public Result getObApplySelectListPage(Long pageNum,Long pageSize) {
        Result result = null;
        try {
            String resultData = iObApplyService.getObApplySelectListPage(pageNum,pageSize);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
