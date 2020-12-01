package com.jiyeyihe.cre.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.*;
import com.jiyeyihe.cre.webapp.mapper.HpCommentMapper;
import com.jiyeyihe.cre.webapp.mapper.HpResourcesMapper;
import com.jiyeyihe.cre.webapp.service.IHpResourcesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;


@Service
@Transactional
public class HpResourcesServiceImpl extends ServiceImpl<HpResourcesMapper, HpResources> implements IHpResourcesService {

    @Resource
    private HpResourcesMapper hpResourcesMapper;
    @Resource
    private HpCommentMapper hpCommentMapper;


    @Override
    public String getHpResourceSelectListPage(Long pageNum, Long pageSize, String communityName,Long businessId) {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        if (communityName!=null||"".equals(communityName)){
            queryWrapper.like("hi.community_name",communityName).or().like("hi.house_number",communityName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        List<HpInfoVo> roleList = hpResourcesMapper.getHpResourceSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpResourcesMapper.getHpResourceSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);

        return object.toString();
    }


    @Override
    public HpResources getHpResourceById(Long hpInfoId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hs.hp_info_id",hpInfoId);
        return hpResourcesMapper.getHpResourceById(queryWrapper);
    }




    @Override
    public String getHpResourcesSelectList(Long pageNum, Long pageSize, String communityName,Long businessId) {
        JSONObject object = new JSONObject();
        List<HpInfoVo> arrayListInfo = new ArrayList<>();
        List<HpResources> arrayListResources = new ArrayList<>();
        IPage<HpResources> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpResources> queryWrapper = new QueryWrapper<HpResources>();
        if (communityName!=null||"".equals(communityName)){
            queryWrapper.like("hi.community_name",communityName).or().like("hi.house_number",communityName);
        }if (businessId!=null||"".equals(businessId)){
            queryWrapper.eq("hi.business_id",businessId);
        }
        queryWrapper.notInSql("hi.id","select hr.hp_info_id from t_hp_resources hr");
        List<HpResources> selectList = hpResourcesMapper.getHpResourcesSelectList(queryWrapper);
        page.setTotal(selectList.size());
        page.setPages(selectList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) *(pageSize) + " ," + pageSize);
        selectList = hpResourcesMapper.getHpResourcesSelectList(queryWrapper);
        page.setRecords(selectList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",selectList);
        return object.toString();
    }


    @Override
    public int delHpResourcesById(Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("hp_info_id",id);
        int delete = hpResourcesMapper.delete(updateWrapper);
        if (delete>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int addAppHpInfoComment(HpComment hpComment,Long id) {
        hpComment.setHpInfoId(id);
        int update = hpCommentMapper.insert(hpComment);
        if (update>0){
            return 1;
        }
        return 0;
    }

    @Override
    public String getAppHpResourceSelectListPage(Long pageNum, Long pageSize,Long userId) {
        JSONObject object = new JSONObject();
        IPage<HpInfoVo> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<HpInfoVo> queryWrapper = new QueryWrapper<HpInfoVo>();
        queryWrapper.le("sd.id",119).ge("sd.id" ,118);
        queryWrapper.le("sd1.id",133).ge("sd1.id" ,124);
        queryWrapper.le("sd2.id",147).ge("sd2.id" ,145);
        queryWrapper.le("sd3.id",158).ge("sd3.id" ,148);
        if (userId!=null||"".equals(userId)){
            queryWrapper.eq("hr.user_id",userId);
        }
        List<HpInfoVo> roleList = hpResourcesMapper.getAppHpResourceSelectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("hi.id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = hpResourcesMapper.getAppHpResourceSelectList(queryWrapper);
        page.setRecords(roleList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList",roleList);

        return object.toString();
    }
}
