package com.project.common.service;

import com.project.common.dto.UserDto;
import com.project.common.entity.UserEntity;

public interface UserAuthService {
    void signUpUser(UserDto userDto);

    UserEntity loginUser(String id, String password);
}
