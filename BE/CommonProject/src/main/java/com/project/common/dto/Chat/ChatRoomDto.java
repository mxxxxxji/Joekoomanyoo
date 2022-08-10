package com.project.common.dto.Chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDto {

    private int roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션

    public static ChatRoomDto create(int groupSeq){
        ChatRoomDto room = new ChatRoomDto();
        room.roomId = groupSeq;
        return room;
    }
}