package com.project.common.controller;

import com.project.common.dto.AR.StampDto;
import com.project.common.service.ARService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("ARController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ar")
public class ARController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final ARService arService;


    /**
     * 스탬프 리스트 불러오기
     *
     * @param
     * @return List
     */

    @ApiOperation(value = "스탬프 리스트 불러오기", response = List.class)
    @GetMapping("/stamp/list")
    public ResponseEntity<?> listStamp() {
        List<StampDto> list = arService.listStamp();
        if (list == null) {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<List<StampDto>>(list, HttpStatus.OK);
        }
    }

    /**
     * 스탬프 나의 목록에 추가하기
     *
     * @param userSeq, stampSeq
     * @return String
     */

    @ApiOperation(value = "스탬프 나의 목록에 추가하기", response = String.class)
    @PostMapping("/stamp/{userSeq}/{stampSeq}")
    public ResponseEntity<String> plusMyStamp(@PathVariable("userSeq") int userSeq, @PathVariable("stampSeq") int stampSeq) {
        if (arService.plusMyStamp(userSeq, stampSeq)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

}
