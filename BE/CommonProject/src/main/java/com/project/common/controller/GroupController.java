package com.project.common.controller;


import com.project.common.dto.GroupAttributeDto;
import com.project.common.dto.GroupDto;
import com.project.common.service.GroupService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //모임 전체 목록 조회
    @ApiOperation(value = "모임 전체 목록 조회, 모임 정보(GroupDto) 반환")
    @GetMapping("/list")
    public ResponseEntity<List<GroupDto>> getGroupList() throws Exception{
    	return new ResponseEntity<>(groupService.getGroupList(),HttpStatus.OK);
    }
    
    //모임 개설
    @ApiOperation(value = "모임 개설, 모임 개설이 성공하면, 모임 정보(GroupDto) 반환")
    @PostMapping("/add")
    public ResponseEntity<GroupDto> addGroup(@RequestBody GroupDto groupDto){
    	return new ResponseEntity<>(groupService.addGroup(groupDto), HttpStatus.CREATED);
    }
    
    //모임 기본 정보 수정

    @ApiOperation(value = "모임 정보 수정, 모임 정보 수정에 성공하면, 수정한 모임 정보(GroupDto) 반환")
    @PutMapping("/{groupSeq}/update")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("groupSeq") Long groupSeq,@RequestBody GroupDto groupDto){
    	return new ResponseEntity<>(groupService.updateGroup(groupSeq,groupDto),HttpStatus.OK);
    }
    
    //모임 상세 정보 입력 및 수정

    @ApiOperation(value = "모임 상세 정보 수정, 상세 정보 수정에 성공하면, 수정한 모임 상세 정보(GroupAttributeDto) 반환")
    @PutMapping("/{groupSeq}/update-attribute")
    public ResponseEntity<GroupAttributeDto> updateGroupAttribute(@PathVariable("groupSeq") Long groupSeq,@RequestBody GroupAttributeDto groupAttributeDto){
    	return new ResponseEntity<>(groupService.updateGroupAttribute(groupSeq,groupAttributeDto),HttpStatus.OK);
    }
    
    //모임 기본정보 보기

    @ApiOperation(value = "모임 정보 보기, 모임 정보(GroupDto) 반환")
    @GetMapping("{groupSeq}/get")
    public ResponseEntity<GroupDto> getGroupInfo(@PathVariable("groupSeq") Long groupSeq){
    	return new ResponseEntity<>(groupService.getGroupInfo(groupSeq),HttpStatus.OK);
    }
    
    //모임 상세정보 보기

    @ApiOperation(value = "모임 상세 정보 보기, 모임 상세 정보(GroupAttributeDto) 반환")
    @GetMapping("{groupSeq}/get-attribute")
    public ResponseEntity<GroupAttributeDto> getGroupAttribute(@PathVariable("groupSeq") Long groupSeq){
    	return new ResponseEntity<>(groupService.getGroupAttribute(groupSeq),HttpStatus.OK);
    }
    
    //모임 삭제

    @ApiOperation(value = "모임 삭제, 등록한 모임 삭제")
    @DeleteMapping("/{groupSeq}")
    public ResponseEntity<?> deleteGroup(@PathVariable("groupSeq") Long groupSeq){
        groupService.deleteGroup(groupSeq);
    	return new ResponseEntity<>(HttpStatus.OK);
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
}
