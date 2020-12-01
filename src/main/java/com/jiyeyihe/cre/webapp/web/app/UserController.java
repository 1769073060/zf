package com.jiyeyihe.cre.webapp.web.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.utils.ObjectConvert;
import com.jiyeyihe.cre.commons.response.Result;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.User;
import com.jiyeyihe.cre.webapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Slf4j
@Api(tags = "app用户管理")
@RestController
@RequestMapping("app/user")
public class UserController {

    @Resource
    private FileUtils fileUtils;
    @Resource
    private IUserService iUserService;

    @ApiOperation(httpMethod = "POST", value = "修改用户信息")
    @RequestMapping(value = "updateUser")
    public Result updateUser(@RequestBody User user) {
        Result result = null;
        if (isEmpty(user.getMobile())) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("mobile", user.getMobile());
            User userOld = iUserService.getOne(queryWrapper);
            if (userOld == null) {
                iUserService.save(user);
            } else {
                userOld = (User) ObjectConvert.combineObjectCore(user, userOld);
                userOld.setUpdatetime(System.currentTimeMillis());
                iUserService.updateById(userOld);
            }
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }

    @ApiOperation(httpMethod = "POST", value = "获取用户信息，没有找到用户添加")
    @RequestMapping(value = "getUserDetails", method = RequestMethod.POST)
    public Result getUserDetails(String mobilePhone) {
        Result result = null;
        if (isEmpty(mobilePhone)) {
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("mobile", mobilePhone);
            User user = iUserService.getOne(queryWrapper);
            if (user != null) {
                user.setPortrait(user.getPortrait() == null ? null : fileUtils.getIpaURl(user.getPortrait()));
            }
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG);
        }
        return result;
    }
}
