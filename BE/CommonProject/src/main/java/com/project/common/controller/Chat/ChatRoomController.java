package com.project.common.controller.Chat;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Chat.ChatRoomDto;
import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.Request.ReqGroupDto;
import com.project.common.repository.ChatRoomRepository;
import com.project.common.service.Group.GroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/chat")
@Api(tags = {"채팅 방 API"})
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    
    @ApiOperation(value = "채팅방 개설")
    @PostMapping("/room")
    public ResponseEntity<ChatRoomDto> addGroup( @RequestParam int groupSeq){
    	return new ResponseEntity<>(chatRoomRepository.createChatRoom(groupSeq), HttpStatus.CREATED);
    }
   
    @ApiOperation(value = "채팅방 조회")
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomDto> roomInfo( @PathVariable int groupSeq){
    	return new ResponseEntity<>(chatRoomRepository.findRoomById(groupSeq), HttpStatus.OK);
    }
    
 

}