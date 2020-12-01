package com.jiyeyihe.cre.webapp.web.busines;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.Carousel;
import com.jiyeyihe.cre.webapp.entity.HpCustomerViewingRecord;
import com.jiyeyihe.cre.webapp.entity.ObLocation;
import com.jiyeyihe.cre.webapp.service.ICarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "轮播图")
@RestController
@RequestMapping("business/Carousel")
public class CarouselController {

    @Resource
    private ICarouselService iCarouselService;


    @ApiOperation(httpMethod = "POST", value = "添加轮播图")
    @PostMapping(value = "addCarousel")
    public Result addCarousel(@RequestBody Carousel carousel){
        Result result = null;
        Object[] objs={carousel.getCarouselUrl(),carousel.getCarouselName()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            carousel.setCreatetime(System.currentTimeMillis());
            carousel.setUpdatetime(System.currentTimeMillis());
            boolean save = iCarouselService.save(carousel);
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

    @ApiOperation(httpMethod = "GET", value = "删除轮播图")
    @GetMapping("delCarousel")
    public Result delCarousel(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iCarouselService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,MsgConsts.SUCCESS_CODE);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("updateCarousel")
    @ApiOperation(httpMethod = "POST", value = "更新轮播图")
    public Result updateCarousel(@RequestBody Carousel carousel){
        Result result = null;
        if (isEmpty(carousel.getId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            carousel.setUpdatetime(System.currentTimeMillis());
            Carousel carouselServiceById = iCarouselService.getById(carousel.getId());
            carouselServiceById = (Carousel) ObjectConvert.combineObjectCore(carousel,carouselServiceById);
            iCarouselService.updateById(carouselServiceById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @ApiOperation(httpMethod = "GET", value = "查询单个轮播图")
    @GetMapping("getIdCarousel")
    public Result getIdCarousel(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            String selectListById = iCarouselService.getCarouselSelectById(id);
            JSONObject jsonObject = JSON.parseObject(selectListById);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }


    @PostMapping("getCarouselListPage")
    @ApiOperation(httpMethod = "POST", value = "轮播图分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
       })
    public Result getCarouselListPage(Long pageNum,Long pageSize) {
        Result result = null;
        try {
            String resultData = iCarouselService.getCarouselListPage(pageNum,pageSize);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "修改轮播图状态")
    @RequestMapping(value = "updateCarouselStatus")
    public Result updateCarouselStatus(@RequestParam("id") Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            Carousel carouselServiceById = iCarouselService.getById(id);
            if (carouselServiceById.getCarouselStatus()==MsgConsts.ZERO_STATUS){
                carouselServiceById.setCarouselStatus(MsgConsts.FIRST_STATUS);
            }else {
                carouselServiceById.setCarouselStatus(MsgConsts.ZERO_STATUS);
            }
            iCarouselService.updateById(carouselServiceById);
            Carousel selectById = iCarouselService.getById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,selectById);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
