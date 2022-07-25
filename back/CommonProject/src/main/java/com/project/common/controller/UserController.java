package com.project.common.controller;

import com.project.common.dto.UserDto;
import com.project.common.dto.UserRequestLoginDTO;
import com.project.common.service.UserAuthServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Api("UserController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    // 성공, 실패로 결과를 전송을 위한 변수 선언
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final UserAuthServiceImpl userAuthServiceImpl;

   
    private JwtServiceImpl jwtService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUpUser(@RequestBody UserDto userDto) throws Exception{
        // 받은 값 저장하기 ( Entity에 )
        return new ResponseEntity<Boolean>(userAuthServiceImpl.save(userDto), HttpStatus.OK);
    }
    
    
    // 로그인
    @PostMapping("/login")
    public boolean loginUser(@ModelAttribute UserRequestLoginDTO userRequestLoginDTO) throws Exception{
        boolean loginResult = userAuthServiceImpl.login(userRequestLoginDTO);

        // 로그인에 성공하면
        if(loginResult){
            
        } 
        // 로그인에 실패하면
        else{
            
        }
        }
    }
