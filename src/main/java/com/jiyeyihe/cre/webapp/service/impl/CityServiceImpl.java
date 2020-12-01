package com.jiyeyihe.cre.webapp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.City;
import com.jiyeyihe.cre.webapp.mapper.CityMapper;
import com.jiyeyihe.cre.webapp.service.ICityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

}