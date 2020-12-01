package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.consts.MsgConsts;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.entity.ObApply;
import com.jiyeyihe.cre.webapp.mapper.ObApplyMapper;
import com.jiyeyihe.cre.webapp.mapper.ObApplyMapper;
import com.jiyeyihe.cre.webapp.mapper.UserMapper;
import com.jiyeyihe.cre.webapp.service.IObApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ObApplyServiceImpl extends ServiceImpl<ObApplyMapper, ObApply> implements IObApplyService {

    @Resource
    private ObApplyMapper obApplyMapper;
    @Resource
    private FileUtils fileUtils;
    @Resource
    private UserMapper userMapper;

    @Override
    public String getObApplyPendingListPage(Long pageNum, Long pageSize, String merchantType) {
        JSONObject object = new JSONObject();
        IPage<ObApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType != null || "".equals(merchantType)) {
            queryWrapper.like("oa.merchant_type", merchantType);
        }
        queryWrapper.eq("oa.apply_status", MsgConsts.ZERO_STATUS);
        List<ObApplyVo> roleList = obApplyMapper.getObApplySelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("oa.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obApplyMapper.getObApplySelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }


    @Override
    public String getObApplyPassedListPage(Long pageNum, Long pageSize, String merchantType) {
        JSONObject object = new JSONObject();
        IPage<ObApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType != null || "".equals(merchantType)) {
            queryWrapper.like("oa.merchant_type", merchantType);
        }
        queryWrapper.eq("oa.apply_status", MsgConsts.FIRST_STATUS);
        List<ObApplyVo> roleList = obApplyMapper.getObApplySelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("oa.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obApplyMapper.getObApplySelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public int addAppObApplyEnterpriseCertification(HpApply apply) {
        ObApply obApply = new ObApply();
        obApply.setApplyName(apply.getApplyName());
        obApply.setAge(apply.getAge());
        obApply.setIdCard(apply.getIdCard());
        obApply.setBackIdCardUrl(apply.getBackIdCardUrl());
        obApply.setFrontIdCardUrl(apply.getFrontIdCardUrl());
        obApply.setBusinessLicensePictureUrl(apply.getBusinessLicensePictureUrl());
        obApply.setCurrentResidenceProvince(apply.getCurrentResidenceProvince());
        obApply.setCurrentResidenceCity(apply.getCurrentResidenceCity());
        obApply.setCurrentResidenceDistrict(apply.getCurrentResidenceDistrict());
        obApply.setCurrentResidentialAddress(apply.getCurrentResidentialAddress());
        obApply.setPhoneNumber(apply.getPhoneNumber());
        obApply.setApplyRentalType(apply.getApplyRentalType());
        obApply.setUserId(apply.getUserId());

        obApply.setBusinessId(MsgConsts.BusinessId_Status);
        obApply.setCreatetime(System.currentTimeMillis());
        obApply.setUpdatetime(System.currentTimeMillis());
        obApply.setMerchantType(MsgConsts.MERCHANT_CERTIFICATION);
        //已申请
        obApply.setApplyStatus(MsgConsts.ZERO_STATUS);
        int i = obApplyMapper.insert(obApply);
        if (i>0){
            return MsgConsts.SUCCESS_CODE;
        }else {
            return MsgConsts.FAIL_CODE;
        }
    }

    @Override
    public String getObApplyNotPassListPage(Long pageNum, Long pageSize, String merchantType) {
        JSONObject object = new JSONObject();
        IPage<ObApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType != null || "".equals(merchantType)) {
            queryWrapper.eq("oa.merchant_type", merchantType);
        }
        queryWrapper.eq("oa.apply_status", MsgConsts.SECOND_STATUS);
        List<ObApplyVo> roleList = obApplyMapper.getObApplySelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obApplyMapper.getObApplySelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", roleList);
        return object.toString();
    }

    @Override
    public String getByIdObApply(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("oa.id", id);
        ObApply houseApply = obApplyMapper.getObApplyById(queryWrapper);
        String pictureUrl = houseApply.getBusinessLicensePictureUrl();
        houseApply.setFrontIdCardUrl(fileUtils.getIpaURl(houseApply.getFrontIdCardUrl()));
        houseApply.setBackIdCardUrl(fileUtils.getIpaURl(houseApply.getBackIdCardUrl()));
        queryWrapper.eq("oa.id", id);
        Map<String, Object> map = obApplyMapper.getByIdObApply(queryWrapper);
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
    public ObApply getObApplyById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("oa.id", id);
        return obApplyMapper.getObApplyById(queryWrapper);
    }

    @Override
    public int updateObApplyById(ObApply byId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        //如果待审核
        if (byId.getApplyStatus()==MsgConsts.ZERO_STATUS){
            byId.setBusinessId(byId.getId());
            //就修改已通过
            byId.setApplyStatus(MsgConsts.FIRST_STATUS);
        }
        //修改状态
        obApplyMapper.updateById(byId);
        //并且id加到用户表的businessId里面
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
    public String getByIdObApplySelect(Long id) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("oa.id",id);
        ObApply houseApply = obApplyMapper.getObApplyById(queryWrapper);
        String pictureUrl = houseApply.getBusinessLicensePictureUrl();
        houseApply.setFrontIdCardUrl(fileUtils.getIpaURl(houseApply.getFrontIdCardUrl()));
        houseApply.setBackIdCardUrl(fileUtils.getIpaURl(houseApply.getBackIdCardUrl()));

        queryWrapper.eq("oa.id",id);
        Map<String,Object> map  = obApplyMapper.getObApplySelectById(queryWrapper);
        map.put("frontIdCardUrl",houseApply.getFrontIdCardUrl());
        map.put("backIdCardUrl",houseApply.getBackIdCardUrl());
        if ("".equals(pictureUrl)||pictureUrl!=null){
            houseApply.setBusinessLicensePictureUrl(fileUtils.getIpaURl(pictureUrl));
            map.put("businessLicensePictureUrl",houseApply.getBusinessLicensePictureUrl());
        }
        object.put("dataList",map);
        return object.toString();
    }

    @Override
    public String getObApplyListPage(Long pageNum, Long pageSize, String merchantType, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<ObApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObApplyVo> queryWrapper = new QueryWrapper();
        if (merchantType!=null||"".equals(merchantType)){
            queryWrapper.like("oa.merchant_type",merchantType);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("oa.business_id",businessId);
        }
        List<ObApplyVo> roleList = obApplyMapper.getObApplyListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obApplyMapper.getObApplyListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getObApplySelectListPage(Long pageNum, Long pageSize) {
        JSONObject object = new JSONObject();
        IPage<ObApplyVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObApplyVo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("oa.apply_status",MsgConsts.FIRST_STATUS);
        List<ObApplyVo> roleList = obApplyMapper.getObApplySelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obApplyMapper.getObApplySelectListPage(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

}
