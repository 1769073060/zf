package com.jiyeyihe.cre.webapp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.Province;
import com.jiyeyihe.cre.webapp.mapper.ProvinceMapper;
import com.jiyeyihe.cre.webapp.service.IProvinceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper, Province> implements IProvinceService {

}