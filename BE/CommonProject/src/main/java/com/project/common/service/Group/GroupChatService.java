package com.project.common.service.Group;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Chat.GroupChatDto;
import com.project.common.entity.Group.GroupChatEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.repository.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatService{

    private GroupRepository groupRepository;
   
    public String saveMessage(GroupChatDto chat){
    	GroupEntity group = groupRepository.findByGroupSeq(chat.getGroupSeq());
    	if(group==null)
    		return "Fail";
		group.addGroupChat(GroupChatEntity.builder()
				.chatContent(chat.getChatContent())
				.chatImgUrl(chat.getChatImgUrl())
				.createdTime(new Date())
				.userSeq(chat.getUserSeq())
				.build());
	//	groupRepository.save(group);

    	return "Success";
    }
    

}
