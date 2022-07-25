package com.project.common.service;

import com.project.common.dto.UserDto;
import com.project.common.dto.UserRequestLoginDTO;
import com.project.common.dto.UserResponseDTO;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public String insertUser(UserEntity userEntity){
        userRepository.save(userEntity);

        return "good";
    }
}
