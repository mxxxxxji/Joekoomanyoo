package com.project.common.service;

import com.project.common.dto.UserDto;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl {

    private UserRepository userRepository;

    public void signUpUser(UserDto userDto);
}
