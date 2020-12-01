package com.jiyeyihe.cre.webapp.web.pub;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.City;
import com.jiyeyihe.cre.webapp.entity.District;
import com.jiyeyihe.cre.webapp.entity.Province;
import com.jiyeyihe.cre.webapp.service.ICityService;
import com.jiyeyihe.cre.webapp.service.IDistrictService;
import com.jiyeyihe.cre.webapp.service.IProvinceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "通用地区")
@RestController
@RequestMapping("pub/region")
public class RegionController {

    @Resource
    private IProvinceService iProvinceService;
    @Resource
    private ICityService iCityService;
    @Resource
    private IDistrictService iDistrictService;

    @ApiOperation(httpMethod = "GET", value = "获取省份")
    @GetMapping("getProvinceList")
    public Result getProvinceList(){
        Result result = null;
        try {
            List<Province> provinceList = iProvinceService.list();
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,provinceList);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "获取市")
    @GetMapping("getCityList")
    public Result getCityList(String provinceId){
        Result result = null;
        if(isEmpty(provinceId)){
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("province_id",provinceId);
            List<City> cityList = iCityService.list(queryWrapper);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,cityList);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "GET", value = "获取地区")
    @GetMapping("getDistrictList")
    public Result getDistrictList(String cityId){
        Result result = null;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("city_id",cityId);
            List<District> districtList = iDistrictService.list(queryWrapper);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,districtList);
        }catch (Exception e){
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
