package com.project.common.controller.Chat;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.dto.Chat.GroupChatDto;
import com.project.common.dto.Chat.GroupChatRoomDto;
import com.project.common.service.Group.GroupChatRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/chat")
@Api(tags = {"채팅 방 API"})
public class GroupChatRoomController {

    private final GroupChatRoomService groupChatRoomService;
    
    @ApiOperation(value = "채팅방 개설")
    @PostMapping("/room")
    public ResponseEntity<GroupChatRoomDto> createChatRoom(@RequestParam int groupSeq){
    	return new ResponseEntity<>(groupChatRoomService.createChatRoom(groupSeq), HttpStatus.CREATED);
    }
   
    @ApiOperation(value = "채팅방 조회")
    @GetMapping("/room")
    public ResponseEntity<GroupChatRoomDto> ChatRoomInfo(@RequestParam int groupSeq){
    	return new ResponseEntity<>(groupChatRoomService.getChatRoomByGroupSeq(groupSeq), HttpStatus.OK);
    }
    
    // 해당 채팅방에 저장된 최신 메시지 받기
    @GetMapping("/room/message/{groupSeq}")
    public ResponseEntity <List<GroupChatDto>> getMessages(@PathVariable int groupSeq){
    	return new ResponseEntity<>(groupChatRoomService.getMessages(groupSeq), HttpStatus.OK);
    }

}