package com.project.common.controller;

import com.project.common.service.FirebaseCloudMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @PostMapping("/token")
    public String registToken(String token) {
        return token;
    }

    @PostMapping("/broadcast")
    public Integer broadCast(String title, String body) throws IOException {
    }

    @PostMapping("/sendMessageTo")
    public void sendMessageTo(String token, String title, String body) throws IOException {
        firebaseCloudMessageService.sendMessageTo(token, title, body);
    }

    /**
     * 전체 알림 설정 하기
     *
     * @param userSeq, userSetting
     * @return String
     */

    @PutMapping("/{userSeq}/{userSetting}/push-setting")
    @ApiOperation(value = "전체 알림 설정 하기", response = String.class)
    public ResponseEntity<String> pushSetting(@PathVariable("userSeq") int userSeq, @PathVariable("userSetting") char setting){
        if(firebaseCloudMessageService.pushSetting(userSeq,setting)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }
}
