package com.project.common.repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.project.common.dto.Chat.ChatRoomDto;

@Repository
public class ChatRoomRepository {

    private Map<Integer, ChatRoomDto> chatRoomDTOMap;

    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<ChatRoomDto> result = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(result);

        return result;
    }
    
    // 특정 채팅방 조회
    public ChatRoomDto findRoomById(int groupSeq){
        return chatRoomDTOMap.get(groupSeq);
    }
    
    // 채팅방 생성
    public ChatRoomDto createChatRoom(int groupSeq){
    	ChatRoomDto room = ChatRoomDto.create(groupSeq);
        chatRoomDTOMap.put(room.getRoomId(), room);

        return room;
    }
    
}