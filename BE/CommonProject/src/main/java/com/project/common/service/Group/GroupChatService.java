package com.project.common.service.Group;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Chat.ChatRoomDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatService{

    private Map<Integer, ChatRoomDto> chatRoomDTOMap;

    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    
    // 특정 채팅방 조회
    public ChatRoomDto getChatRoomByGroupSeq(int groupSeq){
        return chatRoomDTOMap.get(groupSeq);
    }
    
    // 채팅방 생성
    public ChatRoomDto createChatRoom(int groupSeq){
    	ChatRoomDto room = ChatRoomDto.create(groupSeq);
        chatRoomDTOMap.put(room.getGroupSeq(), room);

        return room;
    }
    

}
