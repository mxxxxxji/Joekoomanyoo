package com.project.common.controller;

import com.project.common.dto.HeritageScrapDto;
import com.project.common.dto.MyDailyMemoDto;
import com.project.common.dto.UserKeywordDto;
import com.project.common.service.HeritageService;
import com.project.common.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("MyPageController")
@RequestMapping("/api/mypage")
public class MyPageController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final HeritageService heritageService;
    private final MyPageService myPageService;

    /**
     * 내 문화 유산 스크랩 목록 불러오기
     *
     * @param
     * @return List
     */

    @GetMapping("/scrap/{userSeq}")
    @ApiOperation(value = "내 문화 유산 스크랩 목록 불러오기", response = List.class)
    public ResponseEntity<?> myScrapList(@PathVariable("userSeq") int userSeq) {
        List<HeritageScrapDto> list = heritageService.myScrapList(userSeq);
        if (list.size() == 0) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    /**
     * 내 문화 유산 스크랩 삭제하기
     *
     * @param userSeq, heritageSeq
     * @return List
     */

    @DeleteMapping("/scrap/{userSeq}/{heritageSeq}")
    @ApiOperation(value = "내 문화 유산 스크랩 삭제하기", response = String.class)
    public ResponseEntity<String> deleteScrap(@PathVariable("userSeq") int userSeq, @PathVariable("heritageSeq") int heritageSeq) {
        if (heritageService.deleteScrap(userSeq, heritageSeq)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 내 키워드 생성하기
     *
     * @param userKeywordDto
     * @return String
     */

    @ApiOperation(value = "내 키워드 생성하기", response = String.class)
    @PostMapping("/keyword")
    public ResponseEntity<String> createKeyword(@RequestBody UserKeywordDto userKeywordDto) {
        if (myPageService.createKeyword(userKeywordDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 내 키워드 리스트 보여주기
     *
     * @param userSeq
     * @return List
     */

    @ApiOperation(value = "내 키워드 리스트 보여주기", response = List.class)
    @GetMapping("/keyword/list/{userSeq}")
    public ResponseEntity<?> listKeyword(@PathVariable("userSeq") int userSeq) {
        List<UserKeywordDto> list = myPageService.listKeyword(userSeq);
        if (list.size() == 0) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    /**
     * 내 키워드 삭제
     *
     * @param myKeywordSeq
     * @return String
     */

    @ApiOperation(value = "내 키워드 삭제", response = String.class)
    @DeleteMapping("/keyword/list/{myKeywordSeq}")
    public ResponseEntity<String> deleteKeyword(@PathVariable("myKeywordSeq") int myKeywordSeq) {
       if(myPageService.deleteKeyword(myKeywordSeq)){
           return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
       }else{
           return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
       }
    }

    /**
     * 데일리 메모 생성
     *
     * @param myDailyMemoDto
     * @return String
     */

    @ApiOperation(value = "데일리 메모 생성", response = String.class)
    @PostMapping("/memo")
    public ResponseEntity<String> createDailyMemo(@RequestBody MyDailyMemoDto myDailyMemoDto) {
        if(myPageService.createDailyMemo(myDailyMemoDto)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }


}
