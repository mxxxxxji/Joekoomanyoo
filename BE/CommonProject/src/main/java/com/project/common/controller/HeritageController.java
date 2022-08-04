package com.project.common.controller;

import com.project.common.dto.Heritage.HeritageDto;
import com.project.common.dto.Heritage.HeritageReivewDto;
import com.project.common.dto.Heritage.HeritageScrapDto;
import com.project.common.service.HeritageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api("HeritageController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heritage")
public class HeritageController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final HeritageService heritageService;

    /**
     * 문화 유산 정보 불러오기
     * @param
     * @return List
     */
    
    @ApiOperation(value="문화 유산 상세 정보 불러오기, 문화유산 번호를 통해 상세정보를 불러온다.", response = List.class)
    @GetMapping("/list")
    public ResponseEntity<List<HeritageDto>> listInfo() throws Exception {
        return new ResponseEntity<List<HeritageDto>>(heritageService.listInfo(), HttpStatus.OK);
    }

    /**
     * 문화 유산 리뷰 작성
     * @param  heritageReivewDto
     * @return String
     */

    @ApiOperation(value = "문화 유산 리뷰 작성 ", response = String.class)
    @PostMapping("/review")
    public ResponseEntity<String> createReview(@ApiParam(value = "회원 번호, 문화 유산 번호", required = true) @RequestBody HeritageReivewDto heritageReivewDto){
        heritageReivewDto.setHeritageReviewSeq(0);
        heritageReivewDto.setHeritageReviewRegistedAt(LocalDateTime.now());

        // 성공적으로 입력된다면
        if(heritageService.createReview(heritageReivewDto)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 문화 유산 리뷰 목록
     * @param
     * @return List
     */

    @ApiOperation(value = "문화 유산 리뷰 목록", response = List.class)
    @GetMapping("/reviews")
    public ResponseEntity<?> reviewList(){
        List<HeritageReivewDto> list = heritageService.reviewList();
        if(list == null){
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
    }

    /**
     * 문화 유산 스크랩 등록
     * @param heritageScrapDto
     * @return String
     */

    @ApiOperation(value = "문화 유산 스크랩 등록", response = String.class)
    @PostMapping("/scrap")
    public ResponseEntity<String> createScrap(@RequestBody HeritageScrapDto heritageScrapDto){
        // 초기값 입력
        heritageScrapDto.setHeritageScrapSeq(0);
        heritageScrapDto.setHeritageScrapRegistedAt(LocalDateTime.now());
        // 성공적으로 입력 되었으면
        if(heritageService.createScrap(heritageScrapDto)){
            return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL,HttpStatus.BAD_REQUEST);
        }
    }
}
