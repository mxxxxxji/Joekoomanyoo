package com.project.common.controller.Group;


import java.util.Date;
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
import com.project.common.dto.Group.GroupScheduleDto;
import com.project.common.dto.Group.Request.ReqGroupScheduleDto;
import com.project.common.service.Group.GroupScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/group")
@Api(tags = {"모임 일정 API"})
public class GroupScheduleController {
    private final GroupScheduleService groupScheduleService;
    private final JwtTokenProvider jwtTokenProvider;
    
    //일정 조회
    @ApiOperation(value = "모임 일정 조회")
    @GetMapping("/{groupSeq}/schedule/list")
    public ResponseEntity<List<GroupScheduleDto>> getScheduleList(@PathVariable("groupSeq") int groupSeq) throws Exception{
    	return new ResponseEntity<>(groupScheduleService.getScheduleList(groupSeq),HttpStatus.OK);
    }
    
    //내 모임 일정 조회
    @ApiOperation(value = "내 모임 일정 조회")
    @GetMapping("/my-schedule")
    public ResponseEntity<List<GroupScheduleDto>> getMyScheduleList(HttpServletRequest request) throws Exception{
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
    	return new ResponseEntity<>(groupScheduleService.getMyScheduleList(userId),HttpStatus.OK);
    }
    
    //일정 등록
    @ApiOperation(value = "모임 일정 생성")	
    @PostMapping("/{groupSeq}/schedule/add")
    public ResponseEntity<String> createGroupSchedule(@RequestBody ReqGroupScheduleDto gsDto, @PathVariable int groupSeq){
    	return new ResponseEntity<>(groupScheduleService.createGroupSchedule(groupSeq,gsDto),HttpStatus.CREATED);
    }
    
    //일정 삭제
   	@ApiOperation(value = "모임 일정 삭제")
   	@DeleteMapping("/{groupSeq}/schedule/delete")
   	public ResponseEntity<String> deleteGroupSchedule(@PathVariable int groupSeq, @RequestParam("gsDateTime") Date gsDateTime){
   		return new ResponseEntity<>(groupScheduleService.deleteGroupSchedule(groupSeq,gsDateTime),HttpStatus.OK);
   	}
    
   	//메모 수정
  	@ApiOperation(value = "모임 일정 수정")
  	@PutMapping("/{groupSeq}/schedule/modify")
  	public ResponseEntity<String> modifyGroupSchedule(@RequestBody ReqGroupScheduleDto gsDto, @PathVariable int groupSeq){
  		return new ResponseEntity<>(groupScheduleService.modifyGroupSchedule(groupSeq,gsDto),HttpStatus.OK);
  	}
}
