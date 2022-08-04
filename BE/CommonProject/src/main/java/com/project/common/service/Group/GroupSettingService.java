package com.project.common.service.Group;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupSettingDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.mapper.GroupMapper;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupSettingService{
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final UserService userService;
	private final GroupService groupService;

	
	//모임 상태 변경
	public GroupDto changeStatus(Long groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findById(groupSeq).orElse(null);
		group.setStatus(groupSettingDto.getGroupStatus());
		return GroupMapper.MAPPER.toDto(groupRepository.save(group));
	}
	
	//모임 활성화 
	public GroupDto changeActive(Long groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findById(groupSeq).orElse(null);
		group.setActive(groupSettingDto.getGroupActive());
		return GroupMapper.MAPPER.toDto(groupRepository.save(group));
	}
	
}
