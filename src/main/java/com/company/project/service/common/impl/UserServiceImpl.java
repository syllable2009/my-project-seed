package com.company.project.service.common.impl;

import com.company.project.dao.common.UserMapper;
import com.company.project.entity.common.User;
import com.company.project.service.common.UserService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/07/30.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userTMapper;

}
