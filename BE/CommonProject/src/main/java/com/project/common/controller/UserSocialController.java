package com.project.common.controller;

import com.project.common.config.JwtTokenProvider;
import com.project.common.dto.UserSignDto;
import com.project.common.entity.UserEntity;
import com.project.common.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api("UserSocialController")
@RequestMapping("/api/user/social")
public class UserSocialController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    /**
     * 일반 회원 가입
     *
     * @param userSignDto
     * @return success / fail
     */
    @ApiOperation(value = "소셜 회원가입", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> socialSignup(@ApiParam(value = "소셜 회원 가입 : 회원정보 ( 아이디, 비밀번호, 닉네임, 성별, 생년월일 ) ", required = true) @RequestBody UserSignDto userSignDto, BindingResult bindingResult) {

        // validation
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }

        userRepository.save(UserEntity.builder()
                .userSeq(0)
                .userId(userSignDto.getUserId())
                .userPassword(passwordEncoder.encode(userSignDto.getUserPassword()))
                .userBirth(userSignDto.getUserBirth())
                .userNickname(userSignDto.getUserNickname())
                .userGender(userSignDto.getUserGender())
                .userRegistedAt(LocalDateTime.now())
                .userUpdatedAt(LocalDateTime.now())
                .socialLoginType("social")
                .fcmToken("")
                .profileImgUrl("")
                .isDeleted('N')
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

    }



    /**
     * 소셜 로그인, JWT 발급
     *
     * @param userInfo
     * @return token
     */

    @ApiOperation(value = "소셜 로그인", response = String.class)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userInfo) {
        UserEntity userEntity = userRepository.findByUserId(userInfo.get("userId"));
        
        // 아이디가 없는 경우
        if(userEntity == null){
            return new ResponseEntity<String>(FAIL+" id", HttpStatus.BAD_REQUEST);
        }

        // 소셜 사용자가 아닌 경우
        if(!userEntity.getSocialLoginType().equals("social")){
            return new ResponseEntity<String>(FAIL+" commonUser", HttpStatus.BAD_REQUEST);
        }


        // 토큰 생성해서 리턴
        String token = jwtTokenProvider.createToken(userEntity.getUserSeq(),userEntity.getUsername(), userEntity.getRoles());
        if(token != null){
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL+" token", HttpStatus.BAD_REQUEST);
        }
    }

}