package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.ObResources;
import com.jiyeyihe.cre.webapp.service.IObResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "我发布的写字楼")
@RestController
@RequestMapping("business/officeBuildingResource")
public class ObResourceController {
    @Resource
    private IObResourcesService iObResourcesService;

    @PostMapping("getObResourceSelectListPage")
    @ApiOperation(httpMethod = "POST", value = "发布房源分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "propertyName", value = "楼盘名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObResourceSelectListPage(Long pageNum, Long pageSize, String propertyName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iObResourcesService.getObResourceSelectListPage(pageNum,pageSize,propertyName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    /**
     * 获取到的id是写字楼信息的主键id，获取到id把它新增添加到  写字楼资源的ObInfoId 里面
     * @param pageNum
     * @param pageSize
     * @param propertyName
     * @return
     */
    @GetMapping("getObResources")
    @ApiOperation(httpMethod = "GET", value = "获取新增发布写字楼里面的弹出框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "propertyName", value = "楼盘名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getObResources(Long pageNum, Long pageSize, String propertyName,Long businessId){
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectList = iObResourcesService.getObResourcesSelectList(pageNum,pageSize,propertyName,businessId);
            JSONObject jsonObject = JSON.parseObject(selectList);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,jsonObject);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "POST", value = "添加发布写字楼")
    @PostMapping(value = "addObResources")
    public Result addObResources(@RequestBody ObResources obResources){
        Result result = null;
        Object[] objs={obResources.getId(),obResources.getDescrip(),obResources.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            obResources.setUserId(MsgConsts.UserId_Status);
            //获取下拉框id ，id是t_ob_info表的主键id,所以这里需要传的是主键id
            obResources.setObInfoId(obResources.getId());
            obResources.setOfficeResourcesStatus(MsgConsts.ZERO_STATUS);
            obResources.setCreatetime(System.currentTimeMillis());
            obResources.setUpdatetime(System.currentTimeMillis());
            iObResourcesService.save(obResources);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 根据发布房源分页查询出的 ObInfoId 删除房源
     * @param id
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "删除写字楼发布的房源")
    @GetMapping("delObResources")
    public Result delObResources(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            iObResourcesService.delObResourcesById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "修改写字楼发布房源状态")
    @RequestMapping(value = "updateObResourceStatus")
    public Result updateObResourceStatus(@RequestParam("obInfoId") Long obInfoId,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(obInfoId,businessId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            ObResources byId = iObResourcesService.getObResourceById(obInfoId);
            if (byId.getOfficeResourcesStatus()==MsgConsts.ZERO_STATUS){
                byId.setUpdatetime(System.currentTimeMillis());
                byId.setOfficeResourcesStatus(MsgConsts.FIRST_STATUS);
            }else{
                byId.setOfficeResourcesStatus(MsgConsts.ZERO_STATUS);
            }
            boolean update = iObResourcesService.updateById(byId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,update);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
