package com.project.common.service;

import com.project.common.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{

    UserEntity saveOrUpdateUser(UserEntity userEntity);
}
