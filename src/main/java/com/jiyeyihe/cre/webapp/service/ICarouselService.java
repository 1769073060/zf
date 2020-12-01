package com.jiyeyihe.cre.webapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.Carousel;

public interface ICarouselService extends IService<Carousel> {


    /**
     * 平台端  查询单个轮播图
     * @param id
     * @return
     * @throws Exception
     */
    public String getCarouselSelectById(Long id) throws Exception;

    /**
     * 平台端 轮播图分页查询
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public String getCarouselListPage(Long pageNum, Long pageSize) throws Exception;


    /**
     * app 轮播图查询
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public String getAppCarouselListPage(Long pageNum, Long pageSize) throws Exception;

}
