package com.project.common.controller.Group;

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

import com.project.common.dto.Group.GroupBasicReqDto;
import com.project.common.dto.Group.GroupJoinReqDto;
import com.project.common.dto.Group.GroupMemberListDto;
import com.project.common.service.Group.GroupMemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group/{groupSeq}")
@Api(tags = {"모임 멤버 API"})
public class GroupMemberController {
    private final GroupMemberService groupMemberService;
    
    //모임 멤버 목록 조회
    @ApiOperation(value = "모임 멤버 목록 조회")
    @GetMapping("/member-list")
    public ResponseEntity<List<GroupMemberDto>> getMemberList(@PathVariable("groupSeq") long groupSeq) throws Exception{
    	return new ResponseEntity<>(groupMemberService.getMemberList(groupSeq),HttpStatus.OK);
    }
    
	//참가 신청
    @ApiOperation(value = "모임 참가 신청")
	@PostMapping("/member-join")
	public ResponseEntity<GroupJoinReqDto> joinGroup(@RequestBody GroupJoinReqDto groupJoinRequestDto, @PathVariable long groupSeq){
		groupMemberService.joinGroup(groupSeq,groupJoinRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	 //탈퇴
	@ApiOperation(value = "모임 탈퇴 / 가입 거절 / 가입 취소 / 강제 퇴장 ")
	@DeleteMapping("/member-leave")
	public ResponseEntity<?> leaveGroup(@RequestBody GroupBasicReqDto groupLeaveRequestDto){
		groupMemberService.leaveGroup(groupLeaveRequestDto);
	 	return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//모임 수락
	@ApiOperation(value = "모임 가입 승인 - memberStatus 0(가입대기)->1(일반회원)")
	@PutMapping("/member-approve")
	public ResponseEntity<?> approveMember(@PathVariable("groupSeq") Long groupSeq,@RequestBody GroupBasicReqDto groupBasicReqDto){
		groupMemberService.approveMember(groupSeq,groupBasicReqDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
