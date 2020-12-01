package com.jiyeyihe.cre.webapp.web.ucm;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpApply;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import com.jiyeyihe.cre.webapp.service.IObApplyService;
import com.jiyeyihe.cre.webapp.service.IObApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "平台端写字楼房东申请")
@RestController
@RequestMapping("ucm/obPlatformSideApply")
public class ObPlatformSideController {

    @Resource
    private IObApplyService iObApplyService;

    @PostMapping("getObApplyPendingListPage")
    @ApiOperation(httpMethod = "POST", value = "平台端写字楼 申请信息待审核分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "merchantType", value = "商家类型:个人/商家", required = false, dataType = "String", paramType = "query")})
    public Result getObApplyPendingListPage(Long pageNum, Long pageSize, String merchantType) {
        Result result = null;
        if (isEmpty(merchantType)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObApplyService.getObApplyPendingListPage(pageNum,pageSize,merchantType);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getObApplyPassedListPage")
    @ApiOperation(httpMethod = "POST", value = "平台端写字楼 申请信息已通过分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "merchantType", value = "商家类型:个人/商家", required = false, dataType = "String", paramType = "query")})
    public Result getObApplyPassedListPage(Long pageNum,Long pageSize,String merchantType) {
        Result result = null;
        if (isEmpty(merchantType)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObApplyService.getObApplyPassedListPage(pageNum,pageSize,merchantType);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getObApplyNotPassListPage")
    @ApiOperation(httpMethod = "POST", value = "平台端写字楼 申请信息未通过分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "merchantType", value = "商家类型:个人/商家", required = false, dataType = "String", paramType = "query")})
    public Result getObApplyNotPassListPage(Long pageNum,Long pageSize,String merchantType) {
        Result result = null;
        if (isEmpty(merchantType)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObApplyService.getObApplyNotPassListPage(pageNum,pageSize,merchantType);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "平台端写字楼 查询单个我的申请信息")
    @GetMapping("getByIdObApply")
    public Result getByIdObApply(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iObApplyService.getByIdObApply(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "平台端写字楼 修改申请信息通过状态")
    @RequestMapping(value = "updateObApplyStatus")
    public Result updateObApplyStatus(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",id);
            ObApply obApply = iObApplyService.getOne(queryWrapper);

            int update = iObApplyService.updateObApplyById(obApply);
            if (update>MsgConsts.ZERO_STATUS){
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);
            }else {
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_MSG);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "平台端写字楼 修改申请信息拒绝状态")
    @RequestMapping(value = "updateObApplyRefuseStatus")
    public Result updateObApplyRefuseStatus(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",id);
            ObApply byId = iObApplyService.getOne(queryWrapper);
            //如果待审核
            if (byId.getApplyStatus()==MsgConsts.ZERO_STATUS){
                //就修改已拒绝
                byId.setApplyStatus(MsgConsts.SECOND_STATUS);
            }
            boolean update = iObApplyService.updateById(byId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,update);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
