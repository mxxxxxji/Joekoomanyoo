package com.project.common.controller.Chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.dto.Chat.ChatMessageDto;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor   
@Api(tags = {"채팅 API"})
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto chatMessage) {
        System.out.println("연결성공");
        chatMessage.setMessage(chatMessage.getSender() + "님이 채팅방에 참여하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getGroupSeq(), chatMessage);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto chatMessage) {

    	
        messagingTemplate.convertAndSend("/sub/chat/room/"+chatMessage.getGroupSeq(),chatMessage);
    }
}