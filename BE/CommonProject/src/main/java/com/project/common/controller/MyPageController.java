package com.project.common.controller;

import com.project.common.dto.HeritageScrapDto;
import com.project.common.service.HeritageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("MyPageController")
@RequestMapping("/api/mypage")
public class MyPageController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final HeritageService heritageService;

    @GetMapping("/scrap/{userSeq}")
    @ApiOperation(value = "내 스크랩 불러오기", response = List.class)
    public ResponseEntity<?> myScrapList(@PathVariable("userSeq") int userSeq){
         List<HeritageScrapDto> list = heritageService.myScrapList(userSeq);
         if(list == null){
             return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
         }else{
             return new ResponseEntity<>(list, HttpStatus.OK);
         }
    }

}
