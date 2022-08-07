package com.project.common.controller.Feed;


import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.dto.Feed.FeedDto;
import com.project.common.service.Feed.FeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/feed")
@Api(tags = {" 피드 API"})
public class FeedController {
    private final FeedService feedService;
    
    //피드 등록
    @ApiOperation(value = "피드 등록")
    @PostMapping("/add/{userSeq}")
    public ResponseEntity<FeedDto> addFeed(@PathVariable("userSeq") int userSeq, @RequestBody FeedDto feedDto){
    	return new ResponseEntity<>(feedService.addFeed(userSeq,feedDto), HttpStatus.CREATED);
    }
    
    //피드 전체 조회
    @ApiOperation(value = "피드 전체 조회")
    @GetMapping("/list")
    public ResponseEntity<List<FeedDto>> getFeedList() throws Exception{
    	return new ResponseEntity<>(feedService.getFeedList(),HttpStatus.OK);
    }
    
    //내 피드 조회
    @ApiOperation(value = "내 피드 조회")
    @GetMapping("/my-feed/{userSeq}")
    public ResponseEntity<List<FeedDto>> getMyFeedList(@PathVariable("userSeq") int userSeq) throws Exception{
    	return new ResponseEntity<>(feedService.getMyFeedList(userSeq),HttpStatus.OK);
    }
    
	//피드 전체 조회 (By 해쉬태그)
    @ApiOperation(value = "피드 조회 by 해쉬태그")
    @GetMapping("/list-by-hashtag")
    public ResponseEntity<List<FeedDto>> getFeedListByTag(@Param("fhTag") String fhTag) throws Exception{
    	return new ResponseEntity<>(feedService.getFeedListByTag(fhTag),HttpStatus.OK);
    }
    
    
    //피드 보기
    @ApiOperation(value = "피드 보기")
    @GetMapping("/{feedSeq}/info")
    public ResponseEntity<FeedDto> getFeedInfo(@PathVariable("feedSeq") int feedSeq){
    	return new ResponseEntity<>(feedService.getFeedInfo(feedSeq),HttpStatus.OK);
    }
    
    //피드 삭제
    @ApiOperation(value = "피드 삭제")
    @DeleteMapping("/{feedSeq}/delete")
    public ResponseEntity<String> deleteFeed(@PathVariable("feedSeq") int feedSeq){
    	return new ResponseEntity<>(feedService.deleteFeed(feedSeq),HttpStatus.OK);
    }
    
    //피드 수정
    @ApiOperation(value = "피드 수정")
    @PutMapping("/{feedSeq}/modify")
    public ResponseEntity<FeedDto> updateFeed(@PathVariable("feedSeq") int feedSeq,@RequestBody FeedDto feedDto){
    	return new ResponseEntity<>(feedService.updateFeed(feedSeq,feedDto),HttpStatus.OK);
    }
    
    //피드 공개/비공개
    @ApiOperation(value = "피드 활성화 여부 - Y(공개), N(비공개)")
    @PutMapping("/{feedSeq}/active")
    public ResponseEntity<FeedDto> openFeed(@PathVariable("feedSeq") int feedSeq, @Param("feedOpen") char feedOpen){
    	return new ResponseEntity<>(feedService.openFeed(feedSeq,feedOpen),HttpStatus.OK);
    }

}
