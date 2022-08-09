package com.project.common.controller.Group;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupMyListDto;
import com.project.common.service.Group.GroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 관리 API"})
public class GroupController {
    private final GroupService groupService;
    private final JwtTokenProvider jwtTokenProvider;
    
    //모임 개설
    @ApiOperation(value = "모임 개설")
    @PostMapping("/add")
    public ResponseEntity<GroupDto> addGroup(HttpServletRequest request, @RequestBody GroupDto groupDto){
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));

    	return new ResponseEntity<>(groupService.addGroup(userId,groupDto), HttpStatus.CREATED);
    }
    
    //모임 목록 조회
    @ApiOperation(value = "모임 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<List<GroupDto>> getGroupList() throws Exception{
    	return new ResponseEntity<>(groupService.getGroupList(),HttpStatus.OK);
    }
    
    //모임 정보 보기
    @ApiOperation(value = "모임 정보 조회")
    @GetMapping("/{groupSeq}/info")
    public ResponseEntity<GroupDto> getGroupInfo(@PathVariable("groupSeq") int groupSeq){
    	return new ResponseEntity<>(groupService.getGroupInfo(groupSeq),HttpStatus.OK);
    }
    
    //모임 삭제
    @ApiOperation(value = "모임 삭제")
    @DeleteMapping("/{groupSeq}/delete")
    public ResponseEntity<String> deleteGroup(HttpServletRequest request,@PathVariable("groupSeq") int groupSeq){  
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
   	 	return new ResponseEntity<>(groupService.deleteGroup(userId,groupSeq),HttpStatus.OK);
    }
    
    //모임 정보 수정
    @ApiOperation(value = "모임 정보 수정")
    @PutMapping("/{groupSeq}/modify")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("groupSeq") int groupSeq, @RequestBody GroupDto groupDto){
    	return new ResponseEntity<>(groupService.updateGroup(groupSeq,groupDto),HttpStatus.OK);
    }
    
    //내 모임 목록 조회
    @ApiOperation(value = "내 모임 조회")
    @GetMapping("/my-group")
    public ResponseEntity<List<GroupMyListDto>> getMyGroupList(HttpServletRequest request) throws Exception{
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
   	 	return new ResponseEntity<>(groupService.getMyGroupList(userId),HttpStatus.OK);
    }
    //모임 테마 변경
    @ApiOperation(value = "모임 테마 변경")
    @PutMapping("/{groupSeq}/modify/image")
    public ResponseEntity<String> updateGroup(@PathVariable("groupSeq") int groupSeq,@RequestParam("groupImgUrl") String groupImgUrl){
    	return new ResponseEntity<>(groupService.updateGroupImage(groupSeq,groupImgUrl),HttpStatus.OK);
    }

}
