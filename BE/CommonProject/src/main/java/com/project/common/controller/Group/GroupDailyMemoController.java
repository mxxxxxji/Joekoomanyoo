package com.project.common.controller.Group;


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

import com.project.common.dto.Group.GroupDailyMemoDto;
import com.project.common.service.Group.GroupDailyMemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group/{groupSeq}")
@Api(tags = {" 모임 데일리메모 API"})
public class GroupDailyMemoController {
    private final GroupDailyMemoService groupDailyMemoService;
    
    //메모 조회
    @ApiOperation(value = "모임 데일리 메모 조회")
    @GetMapping("/memo/list")
    public ResponseEntity<List<GroupDailyMemoDto>> getMemberList(@PathVariable("groupSeq") int groupSeq) throws Exception{
    	return new ResponseEntity<>(groupDailyMemoService.getMemoList(groupSeq),HttpStatus.OK);
    }
    
    //메모 등록
    @ApiOperation(value = "모임 데일리 메모 생성")
    @PostMapping("/memo/create")
    public ResponseEntity<GroupDailyMemoDto> createGroupMemo(@PathVariable int groupSeq, @RequestBody GroupDailyMemoDto gdmDto){
    	return new ResponseEntity<>(groupDailyMemoService.createGroupMemo(groupSeq,gdmDto),HttpStatus.CREATED);
    }
    
    //메모 삭제
   	@ApiOperation(value = "모임 데일리 메모 삭제")
   	@DeleteMapping("/memo/delete")
   	public ResponseEntity<?> deleteDailyMemo(@PathVariable int groupSeq, @Param("gdmDate") int gdmDate ){
   		groupDailyMemoService.deleteGroupMemo(groupSeq,gdmDate);
   	 	return new ResponseEntity<>(HttpStatus.OK);
   	}
    
   	//메모 수정
  	@ApiOperation(value = "모임 데일리 메모 수정")
  	@PutMapping("/memo/modify")
  	public ResponseEntity<GroupDailyMemoDto> modifyDailyMemo(@RequestBody GroupDailyMemoDto gdmDto, @PathVariable int groupSeq){
  		return new ResponseEntity<>(groupDailyMemoService.modifyGroupMemo(groupSeq,gdmDto),HttpStatus.OK);
  	}
}
