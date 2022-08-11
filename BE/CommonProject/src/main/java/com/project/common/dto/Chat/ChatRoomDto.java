package com.project.common.dto.Chat;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {

    private int groupSeq;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDto create(int groupSeq){
        ChatRoomDto chatRoom = new ChatRoomDto();
        chatRoom.groupSeq = groupSeq;
        return chatRoom;
    }
}