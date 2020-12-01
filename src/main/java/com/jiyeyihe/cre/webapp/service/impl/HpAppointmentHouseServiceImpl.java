package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.HpAppointmentHouse;
import com.jiyeyihe.cre.webapp.mapper.HpAppointmentHouseMapper;
import com.jiyeyihe.cre.webapp.service.IHpAppointmentHouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 租房看房状态表
 */
@Service
@Transactional
public class HpAppointmentHouseServiceImpl extends ServiceImpl<HpAppointmentHouseMapper, HpAppointmentHouse> implements IHpAppointmentHouseService {

}
