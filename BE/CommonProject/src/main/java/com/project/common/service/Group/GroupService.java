package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupMyListDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.mapper.GroupMapper;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService{
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	
	//모임 찾기
	public GroupEntity findGroup(long groupSeq) {
		GroupEntity findGroup = groupRepository.findByGroupSeq(groupSeq);
	    if (findGroup == null) {
	    	throw new IllegalArgumentException("해당하는 스터디가 없습니다.");
	    }
	    return findGroup;
	}
	
	//모임 개설
	@Transactional
	public GroupDto addGroup(GroupDto groupDto) {
		GroupEntity saved= groupRepository.save(groupDto.toEntity());
		saved.addGroupMember(GroupMemberEntity.builder().memberAppeal("방장").userSeq(1111).memberStatus(2).build());
		groupRepository.save(saved);
		return GroupMapper.MAPPER.toDto(saved);
	}
	
	//모임 목록 조회
	public List<GroupDto> getGroupList(){
		List<GroupEntity> groupList=groupRepository.findAll();
		return GroupMapper.MAPPER.toDtoList(groupList);
	}
	
	//모임 정보 보기
	public GroupDto getGroupInfo(Long groupSeq) {
		GroupEntity groupInfo=groupRepository.findById(groupSeq).orElse(null);
		return GroupMapper.MAPPER.toDto(groupInfo);
	}
	
	//모임 삭제
	public void deleteGroup(long groupSeq){
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				groupMemberRepository.deleteByUserSeq(entity.getUserSeq());
			}
		}
		groupRepository.deleteById(groupSeq);
	}
	
	//모임 정보 수정
	public GroupDto updateGroup(Long groupSeq,GroupDto groupDto) {
		GroupEntity oldGroup =groupRepository.findById(groupSeq).orElse(null);
		GroupDto updateGroup=new GroupDto();
		updateGroup=groupDto;
		if(oldGroup != null) {
			updateGroup.setGroupSeq(oldGroup.getGroupSeq());
			updateGroup.setCreatedTime(oldGroup.getCreatedTime());
			groupRepository.save(updateGroup.toEntity());
		}
		return updateGroup;
	}
	
	//내 모임 조회
	public List<GroupMyListDto> getMyGroupList(long userSeq){
		List<GroupMyListDto> groupList=new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==userSeq)
				groupList.add(new GroupMyListDto(entity));
		}
		return groupList;
	}

}
