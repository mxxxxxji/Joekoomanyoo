package com.project.common.controller;

import com.project.common.dto.UserDto;
import com.project.common.entity.UserEntity;
import com.project.common.service.UserServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Api("UserController")
@RestController
@RequiredArgsConstructor    // autowired 안써도됨
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<?> insertUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // DTO에 저장된 값을 Entity 값을 바꾸기
        // Service와 Controller 이동은 DTO
        // Controller와 DB 이동은 Entity
        UserEntity userEntity = userService.saveOrUpdateUser(userDto.toEntity());

        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }

}
