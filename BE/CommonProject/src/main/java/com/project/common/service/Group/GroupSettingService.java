package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupSettingDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupSettingService{
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	private final GroupMemberRepository groupMemberRepository;
	
	
	//모임 상태 변경
	public String changeStatus(int groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findById(groupSeq).orElse(null);
		group.setGroupStatus(groupSettingDto.getGroupStatus());
		groupRepository.save(group);
		
		//알림 받을 인원들
		List<UserEntity> users = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll())
			if(entity.getGroup().getGroupSeq()==groupSeq)
				users.add(userRepository.findByUserSeq(entity.getUserSeq()));
		
		char status = groupSettingDto.getGroupStatus();
		if(status=='R')
			System.out.println("모집중");
		else if(status=='O')
			System.out.println("모임 진행");
		else if(status=='F')
			System.out.println("모임 종료");	
		
		return "Success";
	}
	
	//모임 활성화 여부 변경
	public String changeActive(int groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findById(groupSeq).orElse(null);
		group.setGroupActive(groupSettingDto.getGroupActive());
		groupRepository.save(group);
		return "Success";
	}
	
}
