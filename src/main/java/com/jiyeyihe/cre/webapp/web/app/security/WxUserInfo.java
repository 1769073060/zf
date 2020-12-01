package com.jiyeyihe.cre.webapp.web.app.security;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiyeyihe.cre.commons.AesUtil;
import com.jiyeyihe.cre.commons.http.HttpUtils;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "获取微信数据")
@RestController
@RequestMapping("app/wx")
public class WxUserInfo {

    @Resource
    private Environment environment;
    @Resource
    private RedisTemplate redisTemplate;


    @PostMapping("getOpenid")
    @ApiOperation(httpMethod = "POST", value = "获取openid")
    public Result getOpenid(String jsCode){
        Result result = null;
        JSONObject resultJson = new JSONObject();
        try {
            String openid = null;
            String sessionId = null;
            StringBuffer url = new StringBuffer();
            url.append("https://api.weixin.qq.com/sns/jscode2session?");
            url.append("appid="+environment.getProperty("wx.appId")+"&");
            url.append("secret="+environment.getProperty("wx.secret")+"&");
            url.append("js_code="+jsCode+"&");
            url.append("grant_type=authorization_code");
            String data = HttpUtils.doGetRequest(url.toString());
            JSONObject jsonObject = JSON.parseObject(data);
            if(jsonObject.containsKey("session_key")){
                openid  = jsonObject.getString("openid");
                redisTemplate.opsForValue().set(openid,jsonObject.getString("session_key"),300000, TimeUnit.SECONDS);

                String uuid = UUID.randomUUID().toString().replace("-", "");
                redisTemplate.opsForValue().set("opt_"+openid,uuid);
                String session = uuid + openid;
                byte[] textByte = session.getBytes("UTF-8");
                Base64.Encoder encoder = Base64.getEncoder();
                sessionId = encoder.encodeToString(textByte);
            }
            resultJson.put("openid",openid);
            resultJson.put("sessionId",sessionId);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,resultJson);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @PostMapping("getUserInfo")
    @ApiOperation(httpMethod = "POST", value = "获取用户信息")
    public Result getUserInfo(String encryptedData,String iv,String openid){
        Result result = null;
        try {
            Object sessionKey = redisTemplate.opsForValue().get(openid);
            AesUtil util = new AesUtil(environment.getProperty("wx.appId"), sessionKey.toString());
            String data = util.decryptData(encryptedData, iv);
            JSONObject dataResult = JSON.parseObject(data);
            result = new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,dataResult);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

}
