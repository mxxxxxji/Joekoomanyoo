package com.project.common.controller;

import com.project.common.dto.Push.FcmRequestDto;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.FirebaseCloudMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push")
public class FcmTokenController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserRepository userRepository;

    /**
     * 메세지 전송
     *
     * @param fcmRequestDto
     * @return String
     */

    @PostMapping("/sendMessageTo")
    public ResponseEntity<?> sendMessageTo(@RequestBody FcmRequestDto fcmRequestDto) throws IOException {
        firebaseCloudMessageService.sendMessageTo(fcmRequestDto.getTargetToken(), fcmRequestDto.getTitle(), fcmRequestDto.getBody());
        System.out.println("알림 : " + fcmRequestDto.getTargetToken() + " "
                + fcmRequestDto.getTitle() + " " + fcmRequestDto.getBody());

        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    /**
     * 전체 알림 설정 하기
     *
     * @param userSeq, userSetting
     * @return String
     */

    @PutMapping("/{userSeq}/{userSetting}/push-setting")
    @ApiOperation(value = "전체 알림 설정 하기", response = String.class)
    public ResponseEntity<String> pushSetting(@PathVariable("userSeq") int userSeq, @PathVariable("userSetting") char setting) {
        if (firebaseCloudMessageService.pushSetting(userSeq, setting)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 토큰 받고 User DB에 저장
     *
     * @param userSeq,fcmToken
     * @return String
     */

    @GetMapping("/token/{userSeq}/{fcmToken}")
    @ApiOperation(value = "토큰 받고 User DB에 저장", response = String.class)
    public ResponseEntity<String> saveToken(@PathVariable("userSeq") int userSeq, @PathVariable("fcmToken") String fcmToken) {
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        if (userEntity == null) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        } else {
            userEntity.setFcmToken(fcmToken);
            userRepository.save(userEntity);
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
    }
}
