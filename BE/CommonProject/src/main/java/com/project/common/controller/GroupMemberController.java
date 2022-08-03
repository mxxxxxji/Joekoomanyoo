package com.project.common.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.JwtTokenProvider;
import com.project.common.dto.GroupDto;
import com.project.common.dto.GroupJoinRequestDto;
import com.project.common.dto.GroupLeaveRequestDto;
import com.project.common.dto.GroupMemberDto;
import com.project.common.dto.GroupMemberListDto;
import com.project.common.dto.UserDto;
import com.project.common.service.GroupMemberService;
import com.project.common.service.GroupService;
import com.project.common.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group/{groupSeq}")
public class GroupMemberController {
    private final GroupService groupService;
    private final UserService userService;
    private final GroupMemberService groupMemberService;
    private final JwtTokenProvider jwtTokenProvider;
    
//    //특정 그륩 참가자 목록 조회
//    @ApiOperation(value = "그륩 멤버 목록 조회, 참가자 정보 List<SimpleGroupMemberDto> 반환")
//    @GetMapping("/member")
//    public ResponseEntity<List<GroupMemberListDto>> getMemberList(@PathVariable("groupSeq") long groupSeq) throws Exception{
//    	return new ResponseEntity<>(groupMemberService.getMemberList(groupSeq),HttpStatus.OK);
//    }
//    
    //특정 그륩 참가자 목록 조회
    @ApiOperation(value = "그륩 멤버 목록 조회, 참가자 정보 List<SimpleGroupMemberDto> 반환")
    @GetMapping("/member")
    public ResponseEntity<List<GroupMemberListDto>> getMemberList(@PathVariable("groupSeq") long groupSeq) throws Exception{
    	return new ResponseEntity<>(groupMemberService.getMemberList(groupSeq),HttpStatus.OK);
    }
    
    
//  //참가 신청
//	@ApiOperation(value = "그륩 참가 신청, 참가 정보(GroupDto) 반환")
//	@PostMapping("/join")
//	public ResponseEntity<GroupJoinRequestDto> joinGroup(@RequestHeader(value="Authorization") String token, @PathVariable long groupSeq){
//		String userId = jwtTokenProvider.getUserId(token);
//		UserDto user = userService.find(userId);
//		groupMemberService.joinGroup(groupSeq,user);
//		return new ResponseEntity<>(HttpStatus.CREATED);
//	}
	
	  //참가 신청
		@ApiOperation(value = "그륩 참가 신청, 참가 정보(GroupDto) 반환")
		@PostMapping("/join")
		public ResponseEntity<GroupJoinRequestDto> joinGroup(@RequestBody GroupJoinRequestDto groupJoinRequestDto, @PathVariable long groupSeq){
	//		String userId = jwtTokenProvider.getUserId(token);
		//	UserDto user = userService.find(userId);
			groupMemberService.joinGroup(groupSeq,groupJoinRequestDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	
//	 //탈퇴
//	@ApiOperation(value = "그륩 탈퇴, 참가 정보(GroupDto) 반환")
//	@PostMapping("/leave")
//	public ResponseEntity<?> leaveGroup(@RequestHeader(value="Authorization") String token, @PathVariable long groupSeq){
//		String userId = jwtTokenProvider.getUserId(token);
//		UserDto user = userService.find(userId);
//	
//		groupMemberService.leaveGroup(groupSeq,user);
//	 	return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	 //탈퇴
	@ApiOperation(value = "그륩 탈퇴, 참가 정보(GroupDto) 반환")
	@DeleteMapping("/leave")
	public ResponseEntity<?> leaveGroup(@RequestBody GroupLeaveRequestDto groupLeaveRequestDto){
	
		groupMemberService.leaveGroup(groupLeaveRequestDto);
	 	return new ResponseEntity<>(HttpStatus.OK);
	}
}
    
    

    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
////    //참가 신청
////    @ApiOperation(value = "모임 개설, 모임 개설이 성공하면, 모임 정보(GroupDto) 반환")
////    @PostMapping("/add")
////    public ResponseEntity<?> joinGroup(@PathVariable long groupSeq){
////    	return new ResponseEntity<>(groupService.joinGroup(groupSeq), HttpStatus.CREATED);
////    }
//    
//    // 그륩 탈퇴
//    @ApiOperation(value = "모임 전체 목록 조회, 모임 정보(GroupDto) 반환")
//    @DeleteMapping("/withdraw")
//    public ResponseEntity<Void> withdrawGroup(@PathVariable long groupSeq, @RequestBody UserDto userSignupDto) throws Exception{
//    	groupMemberService.withdrawGroup(groupSeq,userSignupDto);
//    	return new ResponseEntity<>(HttpStatus.OK);
//    }
//    
    
 
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //모임 기본 정보 수정

   
    
//    //모임 시작
//    @PatchMapping("/{groupSeq}/start")
//    public ResponseEntity<?> startGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.startGroup(groupSeq),HttpStatus.OK);
//    }
//    
//  //모임 종료
//    @PatchMapping("/{groupSeq}/finish")
//    public ResponseEntity<?> finishGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.finishGroup(groupSeq),HttpStatus.OK);
//    }
//    
//    //모임 수락
//    @PatchMapping("/{groupSeq}/approve")
//    public ResponseEntity<?> startGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.startGroup(groupSeq),HttpStatus.OK);
//    }
//    
//  //모임 거절
//    @PatchMapping("/{groupSeq}/reject")
//    public ResponseEntity<?> finishGroup(@PathVariable("groupSeq") Long groupSeq){
//    	return new ResponseEntity<>(groupService.finishGroup(groupSeq),HttpStatus.OK);
//    }
//    
//    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    

