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
import com.project.common.dto.Group.GroupMyListDto;
import com.project.common.dto.Group.GroupScheduleDto;
import com.project.common.service.Group.GroupScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group/{groupSeq}")
@Api(tags = {"모임 일정 API"})
public class GroupScheduleController {
    private final GroupScheduleService groupScheduleService;
    
    //일정 조회
    @ApiOperation(value = "모임 일정 조회")
    @GetMapping("/schedule-list")
    public ResponseEntity<List<GroupScheduleDto>> getScheduleList(@PathVariable("groupSeq") long groupSeq) throws Exception{
    	return new ResponseEntity<>(groupScheduleService.getScheduleList(groupSeq),HttpStatus.OK);
    }
    
//    //내 모임 일정 조회
//    @ApiOperation(value = "내 모임 일정 조회")
//    @GetMapping("/mylist/user/{userSeq}")
//    public ResponseEntity<List<GroupScheduleDto>> getMyScheduleList(@PathVariable("userSeq") Long userSeq,@RequestBody GroupBasicReqDto Dto) throws Exception{
//    	return new ResponseEntity<>(groupScheduleService.getMyScheduleList(userSeq,Dto),HttpStatus.OK);
//    }
//    
    //일정 등록
    @ApiOperation(value = "모임 일정 생성")
    @PostMapping("/schedule-create")
    public ResponseEntity<GroupScheduleDto> createGroupSchedule(@RequestBody GroupScheduleDto gsDto, @PathVariable long groupSeq){
    	return new ResponseEntity<>(groupScheduleService.createGroupSchedule(groupSeq,gsDto),HttpStatus.CREATED);
    }
    
    //일정 삭제
   	@ApiOperation(value = "모임 일정 삭제")
   	@DeleteMapping("/schedule-delete")
   	public ResponseEntity<?> deleteGroupSchedule(@RequestBody GroupScheduleDto gsDto, @PathVariable long groupSeq){
   		groupScheduleService.deleteGroupSchedule(groupSeq,gsDto);
   	 	return new ResponseEntity<>(HttpStatus.OK);
   	}
    
   	//메모 수정
  	@ApiOperation(value = "모임 일정 수정")
  	@PutMapping("/schedule-modify")
  	public ResponseEntity<GroupScheduleDto> modifyGroupSchedule(@RequestBody GroupScheduleDto gsDto, @PathVariable long groupSeq){
  		return new ResponseEntity<>(groupScheduleService.modifyGroupSchedule(groupSeq,gsDto),HttpStatus.OK);
  	}
}
