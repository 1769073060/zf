package com.jiyeyihe.cre.webapp.web.pub;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.webapp.entity.Dictionary;
import com.jiyeyihe.cre.webapp.service.IDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "通用字典表")
@RestController
@RequestMapping("pub/dic")
public class DictionaryController {

    @Resource
    private IDictionaryService iDictionaryService;

    @Resource
    private FileUtils fileUtils;

    @ApiOperation(httpMethod = "POST", value = "添加数据字典")
    @PostMapping(value = "addDictionary")
    public Result addDictionary(@RequestBody Dictionary dictionary){
        Result result = null;
        Object[] objs={dictionary.getDescrip(),dictionary.getDicKey(),dictionary.getDicValue()};
        if(isEmpty(objs)){
            result = new Result(MsgConsts.FAIL_CODE,MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            iDictionaryService.save(dictionary);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG);
        }catch (Exception e){
            result = new Result(MsgConsts.FAIL_CODE,e.getMessage());
            log.info(e.getMessage(),e);
        }
        return result;
    }



    @ApiOperation(httpMethod = "GET", value = "删除数据字典")
    @GetMapping("delDictionary")
    public Result delDictionary(Long id) {
        Result result = null;
        if (isEmpty(id)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            iDictionaryService.removeById(id);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("updateDictionary")
    @ApiOperation(httpMethod = "POST", value = "更新数据字典信息")
    public Result updateDictionary(@RequestBody Dictionary dictionary){
        Result result = null;
        if (isEmpty(dictionary.getId())) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            Dictionary dictionaryOld = iDictionaryService.getById(dictionary.getId());
            dictionaryOld = (Dictionary) ObjectConvert.combineObjectCore(dictionary,dictionaryOld);
            iDictionaryService.updateById(dictionaryOld);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }





    @ApiOperation(httpMethod = "GET", value = "根据key查询所有类型")
    @GetMapping(value = "getDictionaryList")
    public Result getDictionaryList(String key) {
        Result result = null;
        if (isEmpty(key)) {
            result = new Result<>(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("dic_key",key);
            queryWrapper.orderByAsc("id");
            List<Dictionary> dictionaryList = iDictionaryService.list(queryWrapper);
            for(Dictionary dic:dictionaryList){
                if(!isEmpty(dic.getUrl())){
                    dic.setUrl(fileUtils.getIpaURl(dic.getUrl()));
                }
            }
            JSONObject resultJson = new JSONObject();
            resultJson.put("dataList",dictionaryList);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, resultJson);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.SUCCESS_MSG);
        }
        return result;
    }

    @GetMapping("getDictionaryListPage")
    @ApiOperation(httpMethod = "POST", value = "字典表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dicKey", value = "字典表key值", required = true, dataType = "String", paramType = "query")})
    public Result getDictionaryListPage(Long pageNum,Long pageSize,String dicKey) {
        Result result = null;
        try {
            String resultData = iDictionaryService.getDictionaryListPage(pageNum,pageSize,dicKey);
            JSONObject jsonObject = JSON.parseObject(resultData);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, jsonObject);
        } catch (Exception e) {
            log.info(e.getMessage());
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
