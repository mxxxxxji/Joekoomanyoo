package com.project.common.controller;

import java.util.List;

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

import com.project.common.config.JwtTokenProvider;
import com.project.common.dto.GroupBasicReqDto;
import com.project.common.dto.GroupJoinReqDto;
import com.project.common.dto.GroupMemberListDto;
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
    
    //특정 그륩 참가자 목록 조회
    @ApiOperation(value = "그륩 멤버 목록 조회, 참가자 정보 List<SimpleGroupMemberDto> 반환")
    @GetMapping("/member")
    public ResponseEntity<List<GroupMemberListDto>> getMemberList(@PathVariable("groupSeq") long groupSeq) throws Exception{
    	return new ResponseEntity<>(groupMemberService.getMemberList(groupSeq),HttpStatus.OK);
    }
    
	//참가 신청
    @ApiOperation(value = "그륩 참가 신청, 참가 정보(GroupDto) 반환")
	@PostMapping("/join")
	public ResponseEntity<GroupJoinReqDto> joinGroup(@RequestBody GroupJoinReqDto groupJoinRequestDto, @PathVariable long groupSeq){
		groupMemberService.joinGroup(groupSeq,groupJoinRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	 //탈퇴
	@ApiOperation(value = "그륩 탈퇴, 참가 정보(GroupDto) 반환")
	@DeleteMapping("/leave")
	public ResponseEntity<?> leaveGroup(@RequestBody GroupBasicReqDto groupLeaveRequestDto){
		groupMemberService.leaveGroup(groupLeaveRequestDto);
	 	return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//모임 수락
	@ApiOperation(value = "모임수락, member status -> 1로 변경")
	@PutMapping("/approve")
	public ResponseEntity<?> approveMember(@PathVariable("groupSeq") Long groupSeq,@RequestBody GroupBasicReqDto groupBasicReqDto){
		groupMemberService.approveMember(groupSeq,groupBasicReqDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}



    
////특정 그륩 참가자 목록 조회
//@ApiOperation(value = "그륩 멤버 목록 조회, 참가자 정보 List<SimpleGroupMemberDto> 반환")
//@GetMapping("/member")
//public ResponseEntity<List<GroupMemberListDto>> getMemberList(@PathVariable("groupSeq") long groupSeq) throws Exception{
//	return new ResponseEntity<>(groupMemberService.getMemberList(groupSeq),HttpStatus.OK);
//}
//
    
////참가 신청
//@ApiOperation(value = "그륩 참가 신청, 참가 정보(GroupDto) 반환")
//@PostMapping("/join")
//public ResponseEntity<GroupJoinRequestDto> joinGroup(@RequestHeader(value="Authorization") String token, @PathVariable long groupSeq){
//	String userId = jwtTokenProvider.getUserId(token);
//	UserDto user = userService.find(userId);
//	groupMemberService.joinGroup(groupSeq,user);
//	return new ResponseEntity<>(HttpStatus.CREATED);
//}
	

// //탈퇴
//@ApiOperation(value = "그륩 탈퇴, 참가 정보(GroupDto) 반환")
//@PostMapping("/leave")
//public ResponseEntity<?> leaveGroup(@RequestHeader(value="Authorization") String token, @PathVariable long groupSeq){
//	String userId = jwtTokenProvider.getUserId(token);
//	UserDto user = userService.find(userId);
//
//	groupMemberService.leaveGroup(groupSeq,user);
// 	return new ResponseEntity<>(HttpStatus.OK);
//}
