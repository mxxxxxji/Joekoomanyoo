package com.project.common.service.Group;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupSettingDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.repository.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupSettingService{
	private final GroupRepository groupRepository;

	//모임 상태 변경
	public String changeStatus(int groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findById(groupSeq).orElse(null);
		group.setGroupStatus(groupSettingDto.getGroupStatus());
		groupRepository.save(group);
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
