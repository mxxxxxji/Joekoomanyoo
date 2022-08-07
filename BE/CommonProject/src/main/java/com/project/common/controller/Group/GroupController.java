package com.project.common.controller.Group;


import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupMyListDto;
import com.project.common.service.Group.GroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 관리 API"})
public class GroupController {
    private final GroupService groupService;
    
    //모임 개설
    @ApiOperation(value = "모임 개설")
    @PostMapping("/add/{userSeq}")
    public ResponseEntity<GroupDto> addGroup(@PathVariable("userSeq") int userSeq, @RequestBody GroupDto groupDto){
    	return new ResponseEntity<>(groupService.addGroup(userSeq,groupDto), HttpStatus.CREATED);
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
    public ResponseEntity<String> deleteGroup(@PathVariable("groupSeq") int groupSeq){  
    	return new ResponseEntity<>(groupService.deleteGroup(groupSeq),HttpStatus.OK);
    }
    
    //모임 정보 수정
    @ApiOperation(value = "모임 정보 수정")
    @PutMapping("/{groupSeq}/modify")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("groupSeq") int groupSeq, @RequestBody GroupDto groupDto){
    	return new ResponseEntity<>(groupService.updateGroup(groupSeq,groupDto),HttpStatus.OK);
    }
    
    //내 모임 목록 조회
    @ApiOperation(value = "내 모임 조회")
    @GetMapping("/my-group/{userSeq}")
    public ResponseEntity<List<GroupMyListDto>> getMyGroupList(@PathVariable("userSeq") int userSeq) throws Exception{
    	return new ResponseEntity<>(groupService.getMyGroupList(userSeq),HttpStatus.OK);
    }
    
}
