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
import com.jiyeyihe.cre.webapp.mapper.ObAppointmentHouseMapper;
import com.jiyeyihe.cre.webapp.mapper.ObCustomerServiceMapper;
import com.jiyeyihe.cre.webapp.mapper.ObCustomerViewingRecordMapper;
import com.jiyeyihe.cre.webapp.mapper.ObInfoMapper;
import com.jiyeyihe.cre.webapp.service.IObCustomerViewingRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Service
@Transactional
public class ObCustomerViewingRecordServiceImpl extends ServiceImpl<ObCustomerViewingRecordMapper, ObCustomerViewingRecord> implements IObCustomerViewingRecordService {

    @Resource
    private ObCustomerViewingRecordMapper obCustomerViewingRecordMapper;
    @Resource
    private ObInfoMapper obInfoMapper;
    @Resource
    private FileUtils fileUtils;
    @Resource
    private ObAppointmentHouseMapper obAppointmentHouseMapper;

    @Override
    public String getObCustomerViewingRecordListPage(Long pageNum, Long pageSize, String propertyName, Long businessId) {
        JSONObject object = new JSONObject();
        IPage<ObCustomerViewingRecordVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (propertyName!=null||"".equals(propertyName)){
            queryWrapper.like("hi.property_name",propertyName);
        }
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hcvr.business_id",businessId);
        }
        List<ObCustomerViewingRecordVo> roleList = obCustomerViewingRecordMapper.getObCustomerViewingRecordSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hcvr.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obCustomerViewingRecordMapper.getObCustomerViewingRecordSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getObCustomerViewingRecordSelectById(Long id, Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hcvr.id",id);
        queryWrapper.eq("hcvr.business_id",businessId);
        Map<String,Object> map  = obCustomerViewingRecordMapper.getObCustomerViewingRecordSelectById(queryWrapper);
        object.put("dataList",map);
        return object.toString();
    }

    @Override
    public ObCustomerViewingRecord getByIdAppObCustomerViewingRecord(Long ObCustomerViewingRecordId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hcvr.id",ObCustomerViewingRecordId);
        return obCustomerViewingRecordMapper.getByIdAppObCustomerViewingRecord(queryWrapper);
    }

    @Override
    public String getAppObCustomerViewingRecordSelectListPage(Long pageNum, Long pageSize, Long userId, Integer viewingStatus) throws Exception {
        JSONObject object = new JSONObject();
        IPage<ObInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 176).ge("sd2.id", 171);
        queryWrapper.le("sd3.id", 170).ge("sd3.id", 169);
        queryWrapper.eq("hcvr.viewing_status", viewingStatus);// 根据状态查询
        queryWrapper.eq("hcvr.user_id",userId);
        queryWrapper.eq("hah.user_id",userId);
        queryWrapper.eq("hcvr.payment_status",MsgConsts.ZERO_STATUS);
        List<ObInfoVo> roleList = obInfoMapper.getAppObCustomerViewingRecordSelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hcvr.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = obInfoMapper.getAppObCustomerViewingRecordSelectListPage(queryWrapper);

        List<ObInfoVo> arrayList = new ArrayList<>();
        for (ObInfoVo ObInfoVo : roleList) {
            if (!isEmpty(ObInfoVo.getCarouselUrl())) {
                ObInfoVo.setCarouselUrl(fileUtils.getManyUrl(ObInfoVo.getCarouselUrl()));
                String carouselUrl = ObInfoVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                ObInfoVo.setCarouselUrl(substring);
            }
            arrayList.add(ObInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }


    @Override
    public String getAppObCustomerViewingRecordById(Long id, Long userId) throws Exception {
        JSONObject object = new JSONObject();
        ObInfo obInfo = obInfoMapper.selectById(id);
        obInfo.setCarouselUrl(fileUtils.getManyUrl(obInfo.getCarouselUrl()));
        String videoUrl = obInfo.getVideoUrl();
        String vrPicturesUrl = obInfo.getVrPicturesUrl();
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 123).ge("sd1.id", 120);
        queryWrapper.le("sd2.id", 133).ge("sd2.id", 124);
        queryWrapper.le("sd3.id", 139).ge("sd3.id", 134);
        queryWrapper.le("sd4.id", 161).ge("sd4.id", 159);
        queryWrapper.le("sd5.id", 176).ge("sd5.id", 171);
        queryWrapper.le("sd6.id", 179).ge("sd6.id", 177);
        queryWrapper.le("sd8.id", 186).ge("sd8.id", 182);
        queryWrapper.le("sd9.id", 192).ge("sd9.id", 187);
        queryWrapper.le("sd10.id", 194).ge("sd10.id", 193);
        queryWrapper.le("sd11.id", 196).ge("sd11.id", 195);
        queryWrapper.eq("hi.id", id);
        queryWrapper.eq("hfh.user_id",userId);
        queryWrapper.eq("hah.user_id",userId);
        Map<String, Object> selectById = obInfoMapper.getAppObCustomerViewingRecordById(queryWrapper);
        selectById.put("carouselUrl", obInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            obInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", obInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            obInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", obInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public String getAppObOrderById(Long id, Long userId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper<ObInfoVo> queryWrapper = new QueryWrapper<ObInfoVo>();
        queryWrapper.le("sd1.id", 123).ge("sd1.id", 120);
        queryWrapper.le("sd2.id", 139).ge("sd2.id", 134);
        queryWrapper.le("sd3.id", 161).ge("sd3.id", 159);
        queryWrapper.le("sd4.id", 176).ge("sd4.id", 171);
        queryWrapper.le("sd5.id", 179).ge("sd5.id", 177);
        queryWrapper.le("sd6.id", 181).ge("sd6.id", 180);
        queryWrapper.le("sd7.id", 186).ge("sd7.id", 182);
        queryWrapper.le("sd8.id", 192).ge("sd8.id", 187);
        queryWrapper.le("sd9.id", 194).ge("sd9.id", 193);
        queryWrapper.le("sd10.id", 196).ge("sd10.id", 195);
        queryWrapper.eq("hi.id", id);//根据列表查询到的客户看房记录id，查询房屋信息
        queryWrapper.eq("hcvr.user_id", userId);
        Map<String, Object> selectById = obInfoMapper.getAppObOrderById(queryWrapper);
        object.put("dataList", selectById);
        return object.toString();
    }


    @Override
    public int updateObCustomerViewingRecordById(Long obCustomerViewingRecordId) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        ObCustomerViewingRecord obCustomerViewingRecord = new ObCustomerViewingRecord();
        obCustomerViewingRecord.setPaymentStatus(MsgConsts.FIRST_STATUS);
        updateWrapper.eq("id",obCustomerViewingRecordId);
        int update = obCustomerViewingRecordMapper.update(obCustomerViewingRecord, updateWrapper);
        if (update>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }
        return MsgConsts.FAIL_CODE;
    }


    @Override
    public int getByIdObUserCustomerViewingRecord(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("payment_status",MsgConsts.ZERO_STATUS);
        List obCustomerViewingRecord = obCustomerViewingRecordMapper.selectList(queryWrapper);
        if (obCustomerViewingRecord.size()!=0){
            return MsgConsts.FIRST_STATUS;
        }return MsgConsts.ZERO_STATUS;
    }

    @Override
    public int delAppObCustomerViewingRecord(Long obCustomerViewingRecordId, Long obAppointmentHouseId) {
        int customerViewingRecordById = obCustomerViewingRecordMapper.deleteById(obCustomerViewingRecordId);
        int appointmentById = obAppointmentHouseMapper.deleteById(obAppointmentHouseId);
        if (customerViewingRecordById>MsgConsts.ZERO_STATUS&&appointmentById>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }return MsgConsts.FAIL_CODE;
    }

}
