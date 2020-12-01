package com.jiyeyihe.cre.webapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiyeyihe.cre.webapp.entity.User;
import com.jiyeyihe.cre.webapp.mapper.UserMapper;
import com.jiyeyihe.cre.webapp.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
}
