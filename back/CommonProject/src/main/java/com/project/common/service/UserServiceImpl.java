package com.project.common.service;

import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 패스워스 바꿔서 변경
    public UserEntity saveOrUpdateUser(UserEntity userEntity){
        userEntity.encodePassword(this.passwordEncoder);
        
        return this.userRepository.save(userEntity);
    }

}
