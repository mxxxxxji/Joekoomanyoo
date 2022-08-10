package com.project.common.temp;
//package com.project.common.controller.Feed;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.project.common.config.Jwt.JwtTokenProvider;
//import com.project.common.dto.Feed.FeedHashtagDto;
//import com.project.common.service.Feed.FeedHashtagService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor   
//@RequestMapping("/api/feed/{feedSeq}/hashtag")
//@Api(tags = {"피드 해쉬태그 API"})
//public class FeedHashtageController {
//    private final FeedHashtagService feedHashtagService;
//    private final JwtTokenProvider jwtTokenProvider;
//    
//    //피드 해쉬태그 목록 조회
//    @ApiOperation(value = "피드 해쉬태그 목록 조회")
//    @GetMapping("/list")
//    public ResponseEntity<List<FeedHashtagDto>> getFeedHashtagList(@PathVariable("feedSeq") int feedSeq) throws Exception{
//    	return new ResponseEntity<>(feedHashtagService.getFeedHashtagList(feedSeq),HttpStatus.OK);
//    }
//    
//    //피드 해쉬태그 등록
//    @ApiOperation(value = "피드 해쉬태그 등록")
//    @PostMapping("/add")
//    public ResponseEntity<String> addFeedHashtag(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq,@RequestBody List<FeedHashtagDto> fhList){
//   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
//    	return new ResponseEntity<>(feedHashtagService.addFeedHashtag(userId,feedSeq,fhList),HttpStatus.CREATED);
//    }
//
//    
//    //피드 해쉬태그 삭제
//   	@ApiOperation(value = "피드 해쉬태그 삭제")
//   	@PostMapping("/delete")
//   	public ResponseEntity<String> deleteFeedHashtag(HttpServletRequest request,@PathVariable("feedSeq") int feedSeq,@RequestBody List<FeedHashtagDto> fhList){
//   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
//   		return new ResponseEntity<>(feedHashtagService.deleteFeedHashtag(userId,feedSeq,fhList),HttpStatus.OK);
//   	}
// 
//}
