package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.ObAppointmentHouse;
import com.jiyeyihe.cre.webapp.mapper.ObAppointmentHouseMapper;
import com.jiyeyihe.cre.webapp.service.IObAppointmentHouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 写字楼看房状态表
 */
@Service
@Transactional
public class ObAppointmentHouseServiceImpl extends ServiceImpl<ObAppointmentHouseMapper, ObAppointmentHouse> implements IObAppointmentHouseService {

}
