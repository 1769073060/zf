package com.jiyeyihe.cre.webapp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.commons.file.FileUtils;
import com.jiyeyihe.cre.webapp.entity.Carousel;
import com.jiyeyihe.cre.webapp.entity.HpInfo;
import com.jiyeyihe.cre.webapp.entity.HpInfoVo;
import com.jiyeyihe.cre.webapp.mapper.CarouselMapper;
import com.jiyeyihe.cre.webapp.service.ICarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jiyeyihe.cre.utils.ChkUtils.isEmpty;

@Service
@Transactional
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Resource
    private CarouselMapper carouselMapper;
    @Resource
    private FileUtils fileUtils;

    @Override
    public String getCarouselSelectById(Long id) throws Exception {
        JSONObject object = new JSONObject();
        Carousel carousel = carouselMapper.selectById(id);
        carousel.setCarouselUrl(fileUtils.getIpaURl(carousel.getCarouselUrl()));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        Map<String, Object> selectById = carouselMapper.getCarouselSelectById(queryWrapper);
        selectById.put("carouselUrl", carousel.getCarouselUrl());
        object.put("dataList", selectById);
        return object.toString();
    }

    @Override
    public String getCarouselListPage(Long pageNum, Long pageSize) throws Exception {
        JSONObject object = new JSONObject();
        IPage<Carousel> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<Carousel>();
        List<Carousel> roleList = carouselMapper.selectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = carouselMapper.selectList(queryWrapper);
        List<Carousel> arrayList = new ArrayList<>();
        for (Carousel carousel : roleList) {
            if (!isEmpty(carousel.getCarouselUrl())) {
                carousel.setCarouselUrl(fileUtils.getIpaURl(carousel.getCarouselUrl()));
                String carouselUrl = carousel.getCarouselUrl();
                carousel.setCarouselUrl(carouselUrl);
            }
            arrayList.add(carousel);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }

    @Override
    public String getAppCarouselListPage(Long pageNum, Long pageSize) throws Exception {
        JSONObject object = new JSONObject();
        IPage<Carousel> page = new Page<>(pageNum, pageSize, false);
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<Carousel>();
        List<Carousel> roleList = carouselMapper.selectList(queryWrapper);
        page.setTotal(roleList.size());
        page.setPages(roleList.size());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit " + (pageNum - 1) * pageSize + " ," + pageSize);
        roleList = carouselMapper.selectList(queryWrapper);
        List<Carousel> arrayList = new ArrayList<>();
        for (Carousel carousel : roleList) {
            if (!isEmpty(carousel.getCarouselUrl())) {
                carousel.setCarouselUrl(fileUtils.getIpaURl(carousel.getCarouselUrl()));
                String carouselUrl = carousel.getCarouselUrl();
                carousel.setCarouselUrl(carouselUrl);
            }
            arrayList.add(carousel);
        }
        page.setRecords(arrayList);
        object.put("total", page.getTotal());
        object.put("pages", page.getPages());
        object.put("currentPage", pageNum);
        object.put("dataList", arrayList);
        return object.toString();
    }
}
