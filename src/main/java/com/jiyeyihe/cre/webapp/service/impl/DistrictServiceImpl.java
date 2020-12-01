package com.jiyeyihe.cre.webapp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.District;
import com.jiyeyihe.cre.webapp.mapper.DistrictMapper;
import com.jiyeyihe.cre.webapp.service.IDistrictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District> implements IDistrictService {

}