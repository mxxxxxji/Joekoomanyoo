package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Chat.GroupChatDto;
import com.project.common.dto.Chat.GroupChatRoomDto;
import com.project.common.entity.Group.GroupChatEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupChatRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatRoomService{

    private Map<Integer, GroupChatRoomDto> chatRoomDTOMap;
    private final GroupChatRepository groupChatRepository;
    private final UserRepository userRepository;
    
    
    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
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
    // 저장 메시지 보내기
    public List<GroupChatDto> getMessages(int groupSeq) {
        List<GroupChatDto> messages =new ArrayList<>();
        for(GroupChatEntity entity: groupChatRepository.findAll()) {
        	UserEntity user = userRepository.findByUserSeq(entity.getUserSeq());
        	if(entity.getGroupSeq()==groupSeq)
        		messages.add(new GroupChatDto(entity,user));
        }
        return messages;
    }

}
