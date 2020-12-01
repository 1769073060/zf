package com.jiyeyihe.cre.webapp.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.HpApply;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import com.jiyeyihe.cre.webapp.service.IHpApplyService;
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
@Api(tags = "app我的申请信息")
@RestController
@RequestMapping("app/appApply")
public class AppApplyController {
    @Resource
    private IHpApplyService iHpApplyService;
    @Resource
    private IObApplyService iObApplyService;

    @ApiOperation(httpMethod = "POST", value = "添加申请信息个人认证")
    @PostMapping(value = "addAppApplyPersonalCertification")
    public Result addAppApplyPersonalCertification(@RequestBody HpApply apply){
        Result result = null;
        Object[] objs={apply.getApplyName(),apply.getAge(), apply.getIdCard(),
                apply.getBackIdCardUrl(),apply.getFrontIdCardUrl(),
                apply.getBusinessLicensePictureUrl(),apply.getCurrentResidenceProvince(),
                apply.getCurrentResidenceCity(),apply.getCurrentResidenceDistrict(),
                apply.getCurrentResidentialAddress(),apply.getPhoneNumber(),
                apply.getApplyRentalType(),apply.getUserId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            apply.setBusinessId(MsgConsts.BusinessId_Status);
            apply.setCreatetime(System.currentTimeMillis());
            apply.setUpdatetime(System.currentTimeMillis());
            apply.setMerchantType(MsgConsts.PERSONAL_AUTHENTICATION);
            apply.setApplyStatus(MsgConsts.ZERO_STATUS);
            boolean save = iHpApplyService.save(apply);
            if (save==true){
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);
            }else {
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.FAIL_MSG);
            }
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "app添加申请信息企业认证")
    @PostMapping(value = "addAppApplyEnterpriseCertification")
    public Result addAppApplyEnterpriseCertification(@RequestBody HpApply apply){
        Result result = null;
        Object[] objs={apply.getApplyName(),apply.getAge(), apply.getIdCard(),
                apply.getBackIdCardUrl(),apply.getFrontIdCardUrl(),
                apply.getCurrentResidenceProvince(),
                apply.getCurrentResidenceCity(),apply.getCurrentResidenceDistrict(),
                apply.getCurrentResidentialAddress(),apply.getPhoneNumber(),
                apply.getApplyRentalType(),apply.getUserId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            if (apply.getApplyRentalType()==MsgConsts.FIRST_STATUS){
                iHpApplyService.addAppHpApplyEnterpriseCertification(apply);
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);
            }else {
                iObApplyService.addAppObApplyEnterpriseCertification(apply);
                result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_MSG);

            }
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }




    @ApiOperation(httpMethod = "GET", value = "删除我的申请信息")
    @GetMapping("delAppApply")
    public Result delApApply(@RequestParam("id") Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iHpApplyService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateAppApply")
    @ApiOperation(httpMethod = "POST", value = "更新我的申请信息")
    public Result updateAppHpApply(@RequestBody HpApply apply){
        Result result = null;
        if (isEmpty(apply.getId(), apply.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            apply.setUserId(MsgConsts.UserId_Status);
            apply.setUpdatetime(System.currentTimeMillis());
            HpApply houseApply = iHpApplyService.getById(apply.getId());
            houseApply = (HpApply) ObjectConvert.combineObjectCore(apply,houseApply);
            iHpApplyService.updateById(houseApply);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "app租房查询单个我的申请信息")
    @GetMapping("getIdAppApply")
    public Result getIdAppHpApply(@RequestParam("id") Long id,@RequestParam("userId") Long userId) {
        Result result = null;
        if (isEmpty(id,userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iHpApplyService.getAppHpApplySelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getAppApplyListPage")
    @ApiOperation(httpMethod = "POST", value = "app我的申请信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "merchantType", value = "商家类型:个人/商家", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query")})
    public Result getAppApplyListPage(Long pageNum,Long pageSize,String merchantType,Long userId) {
        Result result = null;
        if (isEmpty(userId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpApplyService.getAppApplyListPage(pageNum,pageSize,merchantType,userId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



}
