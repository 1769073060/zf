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
import com.jiyeyihe.cre.webapp.mapper.HpAppointmentHouseMapper;
import com.jiyeyihe.cre.webapp.mapper.HpCustomerViewingRecordMapper;
import com.jiyeyihe.cre.webapp.mapper.HpInfoMapper;
import com.jiyeyihe.cre.webapp.service.IHpCustomerViewingRecordService;
import com.jiyeyihe.cre.webapp.service.IHpInfoDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
@Transactional
public class HpCustomerViewingRecordServiceImpl extends ServiceImpl<HpCustomerViewingRecordMapper, HpCustomerViewingRecord> implements IHpCustomerViewingRecordService {

    @Resource
    private HpCustomerViewingRecordMapper hpCustomerViewingRecordMapper;
    @Resource
    private HpInfoMapper hpInfoMapper;
    @Resource
    private FileUtils fileUtils;
    @Resource
    private HpAppointmentHouseMapper hpAppointmentHouseMapper;

    @Override
    public String getHpCustomerViewingRecordListPage(Long pageNum, Long pageSize, String communityName,Long businessId) {
        JSONObject object = new JSONObject();
        IPage<HpCustomerViewingRecordVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (communityName!=null||"".equals(communityName)){
            queryWrapper.like("hi.community_name",communityName);
        }
        //根据商业id查询后台商家对应约看房的用户数据
        if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hcvr.business_id",businessId);
        }
        List<HpCustomerViewingRecordVo> roleList = hpCustomerViewingRecordMapper.getHpCustomerViewingRecordSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hcvr.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpCustomerViewingRecordMapper.getHpCustomerViewingRecordSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);
        return object.toString();
    }

    @Override
    public String getHpCustomerViewingRecordSelectById(Long id,Long businessId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper queryWrapper = new QueryWrapper();
        //根据看房记录的主键id查询单个房子
        queryWrapper.eq("hcvr.id",id);
        //queryWrapper.eq("hi.business_id",businessId);
        queryWrapper.eq("hcvr.business_id",businessId);
        Map<String,Object> map  = hpCustomerViewingRecordMapper.getHpCustomerViewingRecordSelectById(queryWrapper);
        object.put("dataList",map);
        return object.toString();
    }

    @Override
    public HpCustomerViewingRecord getByIdAppHpCustomerViewingRecord(Long HpCustomerViewingRecordId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hcvr.id",HpCustomerViewingRecordId);
        return hpCustomerViewingRecordMapper.getByIdAppHpCustomerViewingRecord(queryWrapper);
    }


    @Override
    public String getAppHpCustomerViewingRecordSelectListPage(Long pageNum, Long pageSize,Long userId,Integer viewingStatus) throws Exception {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();

        queryWrapper.le("sd.id", 119).ge("sd.id", 118);
        queryWrapper.le("sd1.id", 133).ge("sd1.id", 124);
        queryWrapper.le("sd2.id", 147).ge("sd2.id", 145);
        queryWrapper.le("sd3.id", 158).ge("sd3.id", 148);
        queryWrapper.eq("hcvr.viewing_status", viewingStatus);// 根据状态查询
        queryWrapper.eq("hcvr.user_id",userId);
        queryWrapper.eq("hah.user_id",userId);
        queryWrapper.eq("hcvr.payment_status",MsgConsts.ZERO_STATUS);

        List<HpInfoVo> roleList = hpInfoMapper.getAppHpCustomerViewingRecordSelectListPage(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hcvr.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpInfoMapper.getAppHpCustomerViewingRecordSelectListPage(queryWrapper);

        List<HpInfoVo> arrayList = new ArrayList<>();
        for (HpInfoVo hpInfoVo : roleList) {
            if (!isEmpty(hpInfoVo.getCarouselUrl())) {
                hpInfoVo.setCarouselUrl(fileUtils.getManyUrl(hpInfoVo.getCarouselUrl()));
                String carouselUrl = hpInfoVo.getCarouselUrl();
                int indexOf = carouselUrl.indexOf(",");
                String substring = carouselUrl.substring(0, indexOf);
                hpInfoVo.setCarouselUrl(substring);
            }
            arrayList.add(hpInfoVo);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }


    @Override
    public String getAppHpCustomerViewingRecordById(Long id,Long userId) throws Exception {
        JSONObject object = new JSONObject();
        HpInfo hpInfo = hpInfoMapper.selectById(id);
        hpInfo.setCarouselUrl(fileUtils.getManyUrl(hpInfo.getCarouselUrl()));
        String videoUrl = hpInfo.getVideoUrl();
        String vrPicturesUrl = hpInfo.getVrPicturesUrl();
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id", 112).ge("sd.id", 109);
        queryWrapper.le("sd2.id", 117).ge("sd2.id", 113);
        queryWrapper.le("sd3.id", 119).ge("sd3.id", 118);
        queryWrapper.le("sd4.id", 123).ge("sd4.id", 120);
        queryWrapper.le("sd5.id", 133).ge("sd5.id", 124);
        queryWrapper.le("sd6.id", 139).ge("sd6.id", 134);
        queryWrapper.le("sd7.id", 144).ge("sd7.id", 140);
        queryWrapper.le("sd8.id", 147).ge("sd8.id", 145);
        queryWrapper.le("sd9.id", 158).ge("sd9.id", 148);
        queryWrapper.le("sd10.id", 161).ge("sd10.id", 159);
        queryWrapper.le("sd11.id", 168).ge("sd11.id", 164);
        queryWrapper.eq("hi.id", id);//根据列表查询到的客户看房记录id，查询房屋信息
        queryWrapper.eq("hcvr.user_id", userId);
        queryWrapper.eq("hfh.user_id", userId);
        queryWrapper.eq("hah.user_id", userId);
        Map<String, Object> selectById = hpInfoMapper.getAppHpCustomerViewingRecordById(queryWrapper);
        selectById.put("carouselUrl", hpInfo.getCarouselUrl());
        if (!videoUrl.trim().isEmpty()) {
            hpInfo.setVideoUrl(fileUtils.getIpaURl(videoUrl));
            selectById.put("videoUrl", hpInfo.getVideoUrl());
        }
        if (!vrPicturesUrl.trim().isEmpty()) {
            hpInfo.setVrPicturesUrl(fileUtils.getIpaURl(vrPicturesUrl));
            selectById.put("vrPicturesUrl", hpInfo.getVrPicturesUrl());
        }
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public String getAppHpOrderById(Long id, Long userId) throws Exception {
        JSONObject object = new JSONObject();
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id", 112).ge("sd.id", 109);
        queryWrapper.le("sd2.id", 117).ge("sd2.id", 113);
        queryWrapper.le("sd3.id", 119).ge("sd3.id", 118);
        queryWrapper.le("sd4.id", 123).ge("sd4.id", 120);
        queryWrapper.le("sd5.id", 133).ge("sd5.id", 124);
        queryWrapper.le("sd6.id", 139).ge("sd6.id", 134);
        queryWrapper.le("sd7.id", 144).ge("sd7.id", 140);
        queryWrapper.le("sd8.id", 147).ge("sd8.id", 145);
        queryWrapper.le("sd9.id", 158).ge("sd9.id", 148);
        queryWrapper.le("sd10.id", 161).ge("sd10.id", 159);
        queryWrapper.le("sd11.id", 168).ge("sd11.id", 164);
        queryWrapper.eq("hi.id", id);//根据列表查询到的客户看房记录id，查询房屋信息
        queryWrapper.eq("hcvr.user_id", userId);
        Map<String, Object> selectById = hpInfoMapper.getAppHpOrderById(queryWrapper);
        object.put("dataList", selectById);
        return object.toString();    }

    @Override
    public int updateHpCustomerViewingRecordById(Long obCustomerViewingRecordId) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        HpCustomerViewingRecord hpCustomerViewingRecord = new HpCustomerViewingRecord();
        hpCustomerViewingRecord.setPaymentStatus(MsgConsts.FIRST_STATUS);
        updateWrapper.eq("id",obCustomerViewingRecordId);
        int update = hpCustomerViewingRecordMapper.update(hpCustomerViewingRecord, updateWrapper);
        if (update>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }
        return MsgConsts.FAIL_CODE;
    }


    @Override
    public int getByIdHpUserCustomerViewingRecord(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("payment_status",MsgConsts.ZERO_STATUS);
        List hpCustomerViewingRecord = hpCustomerViewingRecordMapper.selectList(queryWrapper);
        if (hpCustomerViewingRecord.size()!=0){
            return MsgConsts.FIRST_STATUS;
        }return MsgConsts.ZERO_STATUS;
    }

    @Override
    public int delAppHpCustomerViewingRecord(Long hpCustomerViewingRecordId, Long hpAppointmentHouseId) {
        int customerViewingRecordById = hpCustomerViewingRecordMapper.deleteById(hpCustomerViewingRecordId);
        int appointmentById = hpAppointmentHouseMapper.deleteById(hpAppointmentHouseId);
        if (customerViewingRecordById>MsgConsts.ZERO_STATUS&&appointmentById>MsgConsts.ZERO_STATUS){
            return MsgConsts.SUCCESS_CODE;
        }return MsgConsts.FAIL_CODE;
    }

}
