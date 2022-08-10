package com.project.common.controller.Chat;




import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.config.Jwt.JwtTokenProvider;
import com.project.common.dto.Chat.ChatMessageDto;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.Group.GroupService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor   
@RequestMapping("/api/chat")
@Api(tags = {"채팅 API"})
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    
    private final UserRepository userRepository;
    
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto chatMessage) {
        System.out.println("연결성공");
        chatMessage.setMessage(chatMessage.getSender() + "님이 채팅방에 참여하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    }

    @MessageMapping(value = "/chat/message")
    public void message(HttpServletRequest request,	ChatMessageDto chatMessage) {
   	 	String userId = jwtTokenProvider.getUserId(request.getHeader("X-AUTH-TOKEN"));
   	 	UserEntity user = userRepository.findByUserId(userId);
    	String nickName=user.getUserNickname();
    	
    	chatMessage.setSender(nickName);
    	chatMessage.setImg(user.getProfileImgUrl());
    	chatMessage.setUserSeq(user.getUserSeq());
    	
        messagingTemplate.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(),chatMessage);
    }
}