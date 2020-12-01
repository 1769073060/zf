package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.HpApply;
import com.jiyeyihe.cre.webapp.entity.HpApplyVo;
import com.jiyeyihe.cre.webapp.entity.User;
import com.jiyeyihe.cre.webapp.mapper.HpApplyMapper;
import com.jiyeyihe.cre.webapp.mapper.UserMapper;
import com.jiyeyihe.cre.webapp.service.IHpApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class HpApplyServiceImpl extends ServiceImpl<HpApplyMapper, HpApply> implements IHpApplyService {

    @Resource
    private HpApplyMapper hpApplyMapper;
    @Resource
    private FileUtils fileUtils;
    @Resource
    private UserMapper userMapper;

    @Override
    public String getAppHpApplySelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ha.id", id);
        HpApply houseApply = hpApplyMapper.getHpApplyById(queryWrapper);
        String pictureUrl = houseApply.getBusinessLicensePictureUrl();
        houseApply.setFrontIdCardUrl(fileUtils.getIpaURl(houseApply.getFrontIdCardUrl()));
        houseApply.setBackIdCardUrl(fileUtils.getIpaURl(houseApply.getBackIdCardUrl()));

        queryWrapper.eq("ha.id", id);
        Map<String, Object> map = hpApplyMapper.getAppHpApplySelectById(queryWrapper);
        map.put("frontIdCardUrl", houseApply.getFrontIdCardUrl());
        map.put("backIdCardUrl", houseApply.getBackIdCardUrl());
        if ("".equals(pictureUrl) || pictureUrl != null) {
            houseApply.setBusinessLicensePictureUrl(fileUtils.getIpaURl(pictureUrl));
            map.put("businessLicensePictureUrl", houseApply.getBusinessLicensePictureUrl());
        }
        object.put("dataList", map);
        return object.toString();
    }


    @Override
    public String getAppApplyListPage(Long pageNum, Long pageSize, String merchantType, Long userId) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpApplyVo> queryWrapper = new QueryWrapper();
        List<HpApplyVo> roleList = hpApplyMapper.getAppHpApplyListPage(queryWrapper,userId);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpApplyMapper.getAppHpApplyListPage(queryWrapper,userId);

        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public int addAppHpApplyEnterpriseCertification(HpApply hpApply) {
        hpApply.setCreatetime(System.currentTimeMillis());
        hpApply.setUpdatetime(System.currentTimeMillis());
        hpApply.setMerchantType(MsgConsts.MERCHANT_CERTIFICATION);
        //已申请
        hpApply.setApplyStatus(MsgConsts.ZERO_STATUS);
        hpApply.setBusinessId(MsgConsts.BusinessId_Status);
        int i = hpApplyMapper.insert(hpApply);
        if (i>0){
            return MsgConsts.SUCCESS_CODE;
        }else {
            return MsgConsts.FAIL_CODE;
        }

    }


    @Override
    public String getHpApplyPendingListPage(Long pageNum, Long pageSize, String merchantType) {
        JSONObject object = new JSONObject();
        IPage<HpApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType != null || "".equals(merchantType)) {
            queryWrapper.like("ha.merchant_type", merchantType);
        }
        queryWrapper.eq("ha.apply_status", MsgConsts.ZERO_STATUS);
        List<HpApplyVo> roleList = hpApplyMapper.getHpApplySelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpApplyMapper.getHpApplySelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }


    @Override
    public String getHpApplyPassedListPage(Long pageNum, Long pageSize, String merchantType) {
        JSONObject object = new JSONObject();
        IPage<HpApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType != null || "".equals(merchantType)) {
            queryWrapper.like("ha.merchant_type", merchantType);
        }
        queryWrapper.eq("ha.apply_status", MsgConsts.FIRST_STATUS);
        List<HpApplyVo> roleList = hpApplyMapper.getHpApplySelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpApplyMapper.getHpApplySelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public String getHpApplyNotPassListPage(Long pageNum, Long pageSize, String merchantType) {
        JSONObject object = new JSONObject();
        IPage<HpApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType != null || "".equals(merchantType)) {
            queryWrapper.eq("ha.merchant_type", merchantType);
        }
        queryWrapper.eq("ha.apply_status", MsgConsts.SECOND_STATUS);
        List<HpApplyVo> roleList = hpApplyMapper.getHpApplySelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpApplyMapper.getHpApplySelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public String getByIdHpApply(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ha.id", id);
        HpApply houseApply = hpApplyMapper.getHpApplyById(queryWrapper);
        String pictureUrl = houseApply.getBusinessLicensePictureUrl();
        houseApply.setFrontIdCardUrl(fileUtils.getIpaURl(houseApply.getFrontIdCardUrl()));
        houseApply.setBackIdCardUrl(fileUtils.getIpaURl(houseApply.getBackIdCardUrl()));
        queryWrapper.eq("ha.id", id);
        Map<String, Object> map = hpApplyMapper.getByIdHpApply(queryWrapper);
        map.put("frontIdCardUrl", houseApply.getFrontIdCardUrl());
        map.put("backIdCardUrl", houseApply.getBackIdCardUrl());
        if ("".equals(pictureUrl) || pictureUrl != null) {
            houseApply.setBusinessLicensePictureUrl(fileUtils.getIpaURl(pictureUrl));
            map.put("businessLicensePictureUrl", houseApply.getBusinessLicensePictureUrl());
        }
        object.put("dataList", map);
        return object.toString();
    }


    @Override
    public HpApply getHpApplyById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ha.id", id);
        return hpApplyMapper.getHpApplyById(queryWrapper);
    }

    @Override
    public int updateHpApplyById(HpApply byId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        //如果待审核
        if (byId.getApplyStatus()==MsgConsts.ZERO_STATUS){
            byId.setBusinessId(byId.getId());
            //就修改已通过
            byId.setApplyStatus(MsgConsts.FIRST_STATUS);
        }
        //修改状态
        hpApplyMapper.updateById(byId);
        //并且id加到用户表的businessId里面
        System.out.println(byId);
        queryWrapper.eq("id", byId.getUserId());
        User user = userMapper.selectOne(queryWrapper);
        user.setBusinessId(byId.getId());
        updateWrapper.eq("id",byId.getUserId());
        int update = userMapper.update(user, updateWrapper);
        if (update>0){
            return MsgConsts.FIRST_STATUS;
        }
        return MsgConsts.FAIL_CODE;
    }

    @Override
    public String getByIdHpApplySelect(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ha.id", id);
        HpApply houseApply = hpApplyMapper.getHpApplyById(queryWrapper);
        String pictureUrl = houseApply.getBusinessLicensePictureUrl();
        houseApply.setFrontIdCardUrl(fileUtils.getIpaURl(houseApply.getFrontIdCardUrl()));
        houseApply.setBackIdCardUrl(fileUtils.getIpaURl(houseApply.getBackIdCardUrl()));

        queryWrapper.eq("ha.id", id);
        Map<String, Object> map = hpApplyMapper.getByIdHpApplySelect(queryWrapper);
        map.put("frontIdCardUrl", houseApply.getFrontIdCardUrl());
        map.put("backIdCardUrl", houseApply.getBackIdCardUrl());
        if ("".equals(pictureUrl) || pictureUrl != null) {
            houseApply.setBusinessLicensePictureUrl(fileUtils.getIpaURl(pictureUrl));
            map.put("businessLicensePictureUrl", houseApply.getBusinessLicensePictureUrl());
        }
        object.put("dataList", map);
        return object.toString();
    }

    @Override
    public String getHpApplyListPage(Long pageNum, Long pageSize, String merchantType, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<HpApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpApply> queryWrapper = new QueryWrapper();
        if (merchantType!=null||"".equals(merchantType)){
            queryWrapper.eq("ha.merchant_type",merchantType);
        }
        queryWrapper.eq("ha.business_id",businessId);
        List<HpApplyVo> roleList = hpApplyMapper.getHpApplyListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpApplyMapper.getHpApplyListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getHpApplySelectListPage(Long pageNum, Long pageSize) {
        JSONObject object = new JSONObject();
        IPage<HpApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpApply> queryWrapper = new QueryWrapper();
        queryWrapper.eq("ha.apply_status",MsgConsts.FIRST_STATUS);
        List<HpApplyVo> roleList = hpApplyMapper.getHpApplySelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpApplyMapper.getHpApplySelectListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }


}