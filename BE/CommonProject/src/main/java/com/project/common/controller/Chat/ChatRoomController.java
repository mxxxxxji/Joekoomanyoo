package com.project.common.controller.Chat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.dto.Chat.ChatRoomDto;
import com.project.common.service.Group.GroupChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/chat")
@Api(tags = {"채팅 방 API"})
public class ChatRoomController {

    private final GroupChatService groupChatService;
    
    @ApiOperation(value = "채팅방 개설")
    @PostMapping("/room/{groupSeq}")
    public ResponseEntity<ChatRoomDto> createChatRoom( @PathVariable int groupSeq){
    	return new ResponseEntity<>(groupChatService.createChatRoom(groupSeq), HttpStatus.CREATED);
    }
   
    @ApiOperation(value = "채팅방 조회")
    @GetMapping("/room/{groupSeq}")
    public ResponseEntity<ChatRoomDto> ChatRoomInfo( @PathVariable int groupSeq){
    	return new ResponseEntity<>(groupChatService.getChatRoomByGroupSeq(groupSeq), HttpStatus.OK);
    }

}