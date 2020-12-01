package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpResources;
import com.jiyeyihe.cre.webapp.service.IHpResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "我发布的房源")
@RestController
@RequestMapping("business/houseResource")
public class HpResourceController {
    @Resource
    private IHpResourcesService iHpResourcesService;

    @PostMapping("getHpResourceSelectListPage")
    @ApiOperation(httpMethod = "POST", value = "发布房源分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "communityName", value = "小区名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpResourceSelectListPage(Long pageNum, Long pageSize, String communityName,Long businessId) {
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String resultData = iHpResourcesService.getHpResourceSelectListPage(pageNum,pageSize,communityName,businessId);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    /**
     * 获取到的id是房屋信息的主键id，获取到id把它新增添加到  房屋资源的HpInfoId 里面
     * @param pageNum
     * @param pageSize
     * @param communityName
     * @return
     */
    @GetMapping("getHpResources")
    @ApiOperation(httpMethod = "GET", value = "获取新增发布房源里面的弹出框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "communityName", value = "小区名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "商业id", required = true, dataType = "Long", paramType = "query")})
    public Result getHpResources(Long pageNum, Long pageSize, String communityName,Long businessId){
        Result result = null;
        if (isEmpty(businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectList = iHpResourcesService.getHpResourcesSelectList(pageNum,pageSize,communityName,businessId);
            JSONObject jsonObject = JSON.parseObject(selectList);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,jsonObject);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }



    @ApiOperation(httpMethod = "POST", value = "添加发布的房源")
    @PostMapping(value = "addHpResources")
    public Result addHpResources(@RequestBody HpResources hpResources){
        Result result = null;
        Object[] objs={hpResources.getId(),hpResources.getDescrip(),hpResources.getBusinessId()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            hpResources.setUserId(MsgConsts.UserId_Status);
            //获取下拉框id ，id是t_hp_info表的主键id,所以这里需要传的是主键id
            hpResources.setHpInfoId(hpResources.getId());
            hpResources.setHouseResourcesStatus(MsgConsts.ZERO_STATUS);
            hpResources.setCreatetime(System.currentTimeMillis());
            hpResources.setUpdatetime(System.currentTimeMillis());
            iHpResourcesService.save(hpResources);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 根据发布房源分页查询出的 HpInfoId 删除房源
     * @param id
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "删除发布的房源")
    @GetMapping("delHpResources")
    public Result delHpResources(@RequestParam("id") Long id,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(id,businessId)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {

            iHpResourcesService.delHpResourcesById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "修改发布房源状态")
    @RequestMapping(value = "updateHpResourceStatus")
    public Result updateHpResourceStatus(@RequestParam("hpInfoId") Long hpInfoId,@RequestParam("businessId") Long businessId) {
        Result result = null;
        if (isEmpty(hpInfoId,businessId)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            HpResources byId = iHpResourcesService.getHpResourceById(hpInfoId);
            if (byId.getHouseResourcesStatus()==MsgConsts.ZERO_STATUS){
                byId.setUpdatetime(System.currentTimeMillis());
                byId.setHouseResourcesStatus(MsgConsts.FIRST_STATUS);
            }else{
                byId.setHouseResourcesStatus(MsgConsts.ZERO_STATUS);
            }
            boolean update = iHpResourcesService.updateById(byId);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,update);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
