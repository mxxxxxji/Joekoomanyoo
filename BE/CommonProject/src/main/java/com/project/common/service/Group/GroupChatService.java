package com.project.common.service.Group;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Chat.GroupChatDto;
import com.project.common.dto.Chat.GroupChatRoomDto;
import com.project.common.entity.Group.GroupChatEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.repository.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatService{

    private Map<Integer, GroupChatRoomDto> chatRoomDTOMap;
    private GroupRepository groupRepository;
    
    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public String saveMessage(GroupChatDto chat){
    	GroupEntity group = groupRepository.findByGroupSeq(chat.getGroupSeq());
    	
		group.addGroupChat(GroupChatEntity.builder()
				.chatContent(chat.getChatContent())
				.chatImgUrl(chat.getChatImgUrl())
				.createdTime(new Date())
				.userSeq(chat.getUserSeq())
				.build());
		groupRepository.save(group);
    	

    	return "Success";
    }
    
    
    // 특정 채팅방 조회
    public GroupChatRoomDto getChatRoomByGroupSeq(int groupSeq){
        return chatRoomDTOMap.get(groupSeq);
    }
    
    // 채팅방 생성
    public GroupChatRoomDto createChatRoom(int groupSeq){
    	GroupChatRoomDto room = GroupChatRoomDto.create(groupSeq);
        chatRoomDTOMap.put(room.getGroupSeq(), room);

        return room;
    }
    

}
