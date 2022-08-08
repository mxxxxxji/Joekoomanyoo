package com.project.common.controller.Feed;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Feed.FeedDto;
import com.project.common.service.Feed.FeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/feed")
@Api(tags = {"피드 API"})
public class FeedController {
    private final FeedService feedService;
    private final JwtTokenProvider jwtTokenProvider;

    //피드 등록
    @ApiOperation(value = "피드 등록")
    @PostMapping("/add")
    public ResponseEntity<FeedDto> addFeed(HttpServletRequest request, @RequestBody FeedDto feedDto){
    	 String token = request.getHeader("X-AUTH-TOKEN");
         if (token == null || !jwtTokenProvider.validateToken(token)) return null;
        String userId = jwtTokenProvider.getUserId(token);
    	
        return new ResponseEntity<>(feedService.addFeed(userId,feedDto), HttpStatus.CREATED);
    }
    
    //피드 전체 조회
    @ApiOperation(value = "피드 전체 조회")
    @GetMapping("/list")
    public ResponseEntity<List<FeedDto>>getFeedList(HttpServletRequest request) throws Exception{
	   	 String token = request.getHeader("X-AUTH-TOKEN");
	     if (token == null || !jwtTokenProvider.validateToken(token)) return null;
    	
	     return new ResponseEntity<>(feedService.getFeedList(),HttpStatus.OK);
    }
    
    //내 피드 조회
    @ApiOperation(value = "내 피드 조회")
    @GetMapping("/my-feed")
    public ResponseEntity<List<FeedDto>> getMyFeedList(HttpServletRequest request) throws Exception{
    	 String token = request.getHeader("X-AUTH-TOKEN");
         if (token == null || !jwtTokenProvider.validateToken(token)) return null;
        String userId = jwtTokenProvider.getUserId(token);
    	
        return new ResponseEntity<>(feedService.getMyFeedList(userId),HttpStatus.OK);
    }
    
	//피드 전체 조회 (By 해쉬태그)
    @ApiOperation(value = "피드 조회 by 해쉬태그")
    @GetMapping("/list-by-hashtag/{fhTag}")
    public ResponseEntity<List<FeedDto>> getFeedListByTag(HttpServletRequest request, @PathVariable("fhTag") String fhTag) throws Exception{
    	 String token = request.getHeader("X-AUTH-TOKEN");
	     if (token == null || !jwtTokenProvider.validateToken(token)) return null;
	     
    	return new ResponseEntity<>(feedService.getFeedListByTag(fhTag),HttpStatus.OK);
    }
    
    
    //피드 보기
    @ApiOperation(value = "피드 보기")
    @GetMapping("/{feedSeq}/info")
    public ResponseEntity<FeedDto> getFeedInfo(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq){
    	 String token = request.getHeader("X-AUTH-TOKEN");
	     if (token == null || !jwtTokenProvider.validateToken(token)) return null;
    	
	     return new ResponseEntity<>(feedService.getFeedInfo(feedSeq),HttpStatus.OK);
    }
    
    //피드 삭제
    @ApiOperation(value = "피드 삭제")
    @DeleteMapping("/{feedSeq}/delete")
    public ResponseEntity<String> deleteFeed(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq){
    	 String token = request.getHeader("X-AUTH-TOKEN");
	     if (token == null || !jwtTokenProvider.validateToken(token))  return null;
	     String userId = jwtTokenProvider.getUserId(token);
	     return new ResponseEntity<>(feedService.deleteFeed(userId,feedSeq),HttpStatus.OK);
    }
    
    //피드 수정
    @ApiOperation(value = "피드 수정")
    @PutMapping("/{feedSeq}/modify")
    public ResponseEntity<String> updateFeed(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq,@RequestBody FeedDto feedDto){
    	 String token = request.getHeader("X-AUTH-TOKEN");
	     if (token == null || !jwtTokenProvider.validateToken(token))  return null;
	     return new ResponseEntity<>(feedService.updateFeed(feedSeq,feedDto),HttpStatus.OK);
    }
    
    //피드 공개/비공개
    @ApiOperation(value = "피드 활성화 여부 - Y(공개), N(비공개)")
    @PutMapping("/{feedSeq}/active/{feedOpen}")
    public ResponseEntity<String> openFeed(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq, @PathVariable("feedOpen") char feedOpen){
    	System.out.println(feedOpen); 
    	String token = request.getHeader("X-AUTH-TOKEN");
	     if (token == null || !jwtTokenProvider.validateToken(token))  return null;
	     return new ResponseEntity<>(feedService.openFeed(feedSeq,feedOpen),HttpStatus.OK);
    }

}
