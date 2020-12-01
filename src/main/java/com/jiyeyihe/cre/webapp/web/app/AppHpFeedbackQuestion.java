package com.jiyeyihe.cre.webapp.web.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpComment;
import com.jiyeyihe.cre.webapp.entity.HpFeedbackQuestion;
import com.jiyeyihe.cre.webapp.service.IHpFeedbackQuestionService;
import com.jiyeyihe.cre.webapp.service.impl.HpFeedbackQuestionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app反馈类型")
@RestController
@RequestMapping("app/appHouseFeedbackQuestionController")
public class AppHpFeedbackQuestion {

    @Resource
    private IHpFeedbackQuestionService iHpFeedbackQuestionService;


    @ApiOperation(httpMethod = "POST", value = "发送反馈类型")
    @RequestMapping(value = "addAppHpFeedbackQuestion")
    public Result addAppHpFeedbackQuestion(@RequestBody  HpFeedbackQuestion hpFeedbackQuestion) {
        Result result = null;
        if (isEmpty(hpFeedbackQuestion.getFeedbackContent(),hpFeedbackQuestion.getFeedbackType())) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpFeedbackQuestion.setCreatetime(System.currentTimeMillis());
            hpFeedbackQuestion.setUpdatetime(System.currentTimeMillis());
            boolean save = iHpFeedbackQuestionService.save(hpFeedbackQuestion);
            if (save == true){
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_CODE);
            }else{
                result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_CODE);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getAppHpFeedbackQuestionListPage")
    @ApiOperation(httpMethod = "POST", value = "反馈类型列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query")})
    public Result getAppHpResourcesListPage(Long pageNum, Long pageSize,Long userId) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpFeedbackQuestionService.getAppHpFeedbackQuestionListPage(pageNum,pageSize,userId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "查询单个我的申请信息")
    @GetMapping("getIdHpFeedbackQuestion")
    public Result getIdHpFeedbackQuestion(Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",id);
            queryWrapper.eq("user_id",userId);
            queryWrapper.orderByDesc("id");
            HpFeedbackQuestion selectListById = iHpFeedbackQuestionService.getOne(queryWrapper);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,selectListById);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
