package com.project.common.controller.Chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.dto.Chat.GroupChatDto;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.Group.GroupChatService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor   
@Api(tags = {"채팅 API"})
public class GroupChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final GroupChatService groupChatService;
    
    @MessageMapping(value = "/chat/message")
    public void message(GroupChatDto chat) {
    	int userSeq=chat.getUserSeq();
    	UserEntity user = userRepository.findByUserSeq(userSeq);
    	chat.setSender(user.getUserNickname());
    	chat.setChatImgUrl(user.getProfileImgUrl());    	
        messagingTemplate.convertAndSend("/sub/chat/room/"+chat.getGroupSeq(),chat);
        
        groupChatService.saveMessage(chat);
    }
}