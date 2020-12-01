package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.Contract;

import com.jiyeyihe.cre.webapp.service.IContractService;
import com.jiyeyihe.cre.webapp.service.IContractService;
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
@Api(tags = "合同模板")
@RestController
@RequestMapping("business/Contract")
public class ContractController {
    @Resource
    private IContractService iContractService;




    @ApiOperation(httpMethod = "POST", value = "添加合同模板")
    @PostMapping(value = "addContract")
    public Result addContract(@RequestBody Contract contract){
        Result result = null;
        Object[] objs={contract.getContractTemplateName(),contract.getContractTemplateContent(),contract.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            contract.setCreatetime(System.currentTimeMillis());
            contract.setUpdatetime(System.currentTimeMillis());
            iContractService.save(contract);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }

    @PostMapping("addByIdContract")
    @ApiOperation(httpMethod = "POST", value = "添加合同图片")
    public Result addByIdContract(@RequestBody Contract contract){
        Result result = null;
        if (isEmpty(contract.getContract())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            Contract maxId = iContractService.listMaxId();
            Contract serviceById = iContractService.getById(maxId.getId());
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            Contract contra = new Contract();
            contra.setContract(contract.getContract());
            updateWrapper.eq("id",serviceById.getId());
            boolean update = iContractService.update(contra, updateWrapper);

            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "删除合同模板")
    @GetMapping("delContract")
    public Result delContract(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iContractService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateContract")
    @ApiOperation(httpMethod = "POST", value = "更新合同模板")
    public Result updateContract(@RequestBody Contract contract){
        Result result = null;
        if (isEmpty(contract.getId(),contract.getBusinessId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            contract.setUpdatetime(System.currentTimeMillis());
            Contract serviceById = iContractService.getById(contract.getId());
            serviceById = (Contract) ObjectConvert.combineObjectCore(contract,serviceById);
            iContractService.updateById(serviceById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("updateByIdContract")
    @ApiOperation(httpMethod = "POST", value = "更新合同图片")
    public Result updateByIdContract(@RequestBody Contract contract){
        Result result = null;
        if (isEmpty(contract.getId(),contract.getContract())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            contract.setUpdatetime(System.currentTimeMillis());
            contract.setContract(contract.getContract());
            updateWrapper.eq("id",contract.getId());
            boolean update = iContractService.update(contract, updateWrapper);

            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询单个合同模板")
    @GetMapping("getIdContract")
    public Result getIdContract(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            String Contract = iContractService.getIdContract(id);
            JSONObject jsonObject = JSON.parseObject(Contract);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getContractListPage")
    @ApiOperation(httpMethod = "POST", value = "合同模板分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "contractTemplateName", value = "合同名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getInfoDetailListPage(Long pageNum,Long pageSize,String contractTemplateName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iContractService.getContractListPage(pageNum,pageSize,contractTemplateName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}

