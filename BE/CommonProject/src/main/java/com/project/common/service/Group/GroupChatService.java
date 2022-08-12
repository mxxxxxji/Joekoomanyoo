package com.project.common.service.Group;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Chat.GroupChatDto;
import com.project.common.entity.Group.GroupChatEntity;
import com.project.common.repository.Group.GroupChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatService{

    private final GroupChatRepository groupChatRepository;
    
    public void saveMessage(GroupChatDto chat){
    	GroupChatEntity entity = chat.toEntity();
    	entity.setCreatedTime(new Date());
    	groupChatRepository.save(entity);
    }
    
    

}
