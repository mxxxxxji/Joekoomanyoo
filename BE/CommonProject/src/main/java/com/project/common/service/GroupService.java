package com.project.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.GroupAttributeDto;
import com.project.common.dto.GroupAttributeMapper;
import com.project.common.dto.GroupDto;
import com.project.common.dto.GroupMapper;
import com.project.common.entity.GroupAttributeEntity;
import com.project.common.entity.GroupEntity;
import com.project.common.repository.GroupAttributeRepository;
import com.project.common.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService{
	private final GroupRepository groupRepository;
	private final GroupAttributeRepository groupAttributeRepository;
	
	//전체 목록 조회
	public List<GroupDto> getGroupList(){
		return GroupMapper.MAPPER.toDtoList(groupRepository.findAll());
	}
	
	//모임 개설
	public GroupDto addGroup(GroupDto groupDto) {
		groupRepository.save(groupDto.toEntity());
		return groupDto;
	}

	//모임 기본 정보 수정
	public GroupDto updateGroup(Long groupSeq,GroupDto groupDto) {
		GroupEntity oldGroup =groupRepository.findById(groupSeq).orElse(null);
		GroupDto updateGroup=new GroupDto();
		if(oldGroup != null) {
			updateGroup.setGroupSeq(oldGroup.getGroupSeq());
			updateGroup.setGroupName(groupDto.getGroupName());
			updateGroup.setAttachSeq(groupDto.getAttachSeq());
			updateGroup.setGroupMaker(groupDto.getGroupMaker());
			updateGroup.setGroupDescription(groupDto.getGroupDescription());
			updateGroup.setGroupMaxCount(groupDto.getGroupMaxCount());
			updateGroup.setGroupAccessType(groupDto.getGroupAccessType());
			updateGroup.setGroupPassword(groupDto.getGroupPassword());
			updateGroup.setGroupStatus(groupDto.getGroupStatus());
			updateGroup.setGroupIsActive(groupDto.getGroupIsActive());
			updateGroup.setGroupCreatedAt(oldGroup.getGroupCreatedAt());
		}else
			updateGroup=groupDto;
		
		groupRepository.save(updateGroup.toEntity());
		
		return updateGroup;
	}

	//모임 상세 정보 수정
	public GroupAttributeDto updateGroupAttribute(Long groupSeq,GroupAttributeDto groupAttributeDto) {
		GroupAttributeEntity oldAttr = groupAttributeRepository.findById(groupSeq).orElse(null);
		
		GroupAttributeDto updateAttr=new GroupAttributeDto();
		if(oldAttr != null) {
			updateAttr.setGroupSeq(oldAttr.getGroupSeq());
			updateAttr.setGaRegion(groupAttributeDto.getGaRegion());
			updateAttr.setGaStartDate(groupAttributeDto.getGaStartDate());
			updateAttr.setGaEndDate(groupAttributeDto.getGaEndDate());
			updateAttr.setGaChildJoin(groupAttributeDto.getGaChildJoin());
			updateAttr.setGaGlobalJoin(groupAttributeDto.getGaGlobalJoin());
			updateAttr.setGaAge(groupAttributeDto.getGaAge());
			updateAttr.setGaCreatedAt(oldAttr.getGaCreatedAt());
		}else
			updateAttr=groupAttributeDto;
		
		groupAttributeRepository.save(updateAttr.toEntity());
		
		return updateAttr;
	}
	
	//모임 기본정보 보기
	public GroupDto getGroupInfo(Long groupSeq) {
		GroupEntity groupInfo=groupRepository.findById(groupSeq).orElse(null);
		return GroupMapper.MAPPER.toDto(groupInfo);
	}
	
	//모임 상세정보 보기
	public GroupAttributeDto getGroupAttribute(Long groupSeq) {
		GroupAttributeEntity groupAttribute=groupAttributeRepository.findById(groupSeq).orElse(null);
		return GroupAttributeMapper.MAPPER.toDto(groupAttribute);
	}
	
	//모임 삭제
	public void deleteGroup(Long groupSeq){
		groupRepository.deleteById(groupSeq);
	}

}