package com.project.common.controller.Group;


import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.dto.Group.GroupDailyMemoDto;
import com.project.common.service.Group.GroupDailyMemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 데일리메모 API"})
public class GroupDailyMemoController {
    private final GroupDailyMemoService groupDailyMemoService;
    
    //메모 조회
    @ApiOperation(value = "모임 데일리 메모 조회")
    @GetMapping("/{groupSeq}/memo/list")
    public ResponseEntity<List<GroupDailyMemoDto>> getMemberList(@PathVariable("groupSeq") int groupSeq) throws Exception{
    	return new ResponseEntity<>(groupDailyMemoService.getMemoList(groupSeq),HttpStatus.OK);
    }
    
    //내 모임 메모 조회
    @ApiOperation(value = "내 모임 데일리 메모 조회")
    @GetMapping("/my-memo/{userSeq}")
    public ResponseEntity<List<GroupDailyMemoDto>> getMyGroupMemoList(@PathVariable("userSeq") int userSeq) throws Exception{
    	return new ResponseEntity<>(groupDailyMemoService.getMyGroupMemoList(userSeq),HttpStatus.OK);
    }
    
    
    //메모 등록
    @ApiOperation(value = "모임 데일리 메모 생성")
    @PostMapping("/{groupSeq}/memo/add")
    public ResponseEntity<String> createGroupMemo(@PathVariable int groupSeq, @RequestBody GroupDailyMemoDto gdmDto){
    	return new ResponseEntity<>(groupDailyMemoService.createGroupMemo(groupSeq,gdmDto),HttpStatus.CREATED);
    }
    
    //메모 삭제
   	@ApiOperation(value = "모임 데일리 메모 삭제")
   	@DeleteMapping("/{groupSeq}/memo/delete")
   	public ResponseEntity<String> deleteDailyMemo(@PathVariable int groupSeq, @RequestParam("gdmDate") Date gdmDate ){
   	 	return new ResponseEntity<>(groupDailyMemoService.deleteGroupMemo(groupSeq,gdmDate),HttpStatus.OK);
   	}
    
   	//메모 수정
  	@ApiOperation(value = "모임 데일리 메모 수정")
  	@PutMapping("/{groupSeq}/memo/modify")
  	public ResponseEntity<String> modifyDailyMemo(@RequestBody GroupDailyMemoDto gdmDto, @PathVariable int groupSeq){
  		return new ResponseEntity<>(groupDailyMemoService.modifyGroupMemo(groupSeq,gdmDto),HttpStatus.OK);
  	}
}
