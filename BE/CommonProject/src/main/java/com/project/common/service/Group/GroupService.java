package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupMyListDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.Group.GroupMapper;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService{
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;

	private final UserRepository userRepository;
	
	//모임 개설
	@Transactional
	public GroupDto addGroup(String userId,GroupDto groupDto) {
		UserEntity user = userRepository.findByUserId(userId);
		GroupEntity saved= groupDto.toEntity();
		saved.setGroupMaster(user.getUserNickname());
	 	saved.setCreatedTime(new Date());
		saved.setUpdatedTime(new Date());
		saved.addGroupMember(GroupMemberEntity.builder()
				.memberAppeal("방장")
				.userSeq(user.getUserSeq())
				.memberStatus(2)
				.memberIsEvaluated('N')
				.createdTime(new Date())
				.updatedTime(new Date())
				.approveTime(new Date())
				.build());
		groupRepository.save(saved);
		user.addGroup(saved);
	 	userRepository.save(user);
		return GroupMapper.MAPPER.toDto(saved);
	}
	
	//모임 목록 조회
	public List<GroupDto> getGroupList(){
		List<GroupEntity> groupList=groupRepository.findAll();
		return GroupMapper.MAPPER.toDtoList(groupList);
	}
	
	//모임 정보 보기
	public GroupDto getGroupInfo(int groupSeq) {
		GroupEntity groupInfo=groupRepository.findById(groupSeq).orElse(null);
		return GroupMapper.MAPPER.toDto(groupInfo);
	}
	
	//모임 삭제
	public String deleteGroup(String userId,int groupSeq){
		UserEntity user = userRepository.findByUserId(userId);
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq && entity.getUserSeq()==user.getUserSeq()) {
		//		groupMemberRepository.deleteByGroupSeqAndUserSeq(groupSeq,user.getUserSeq());
				groupRepository.deleteById(groupSeq);
				return "Success";			
			}
		}
		return "Fail";
	}
	
	//모임 정보 수정
	public GroupDto updateGroup(int groupSeq,GroupDto groupDto) {
		GroupEntity Group =groupRepository.findById(groupSeq).orElse(null);
		Group.setGroupAccessType(groupDto.getGroupAccessType());
		Group.setGroupActive(groupDto.getGroupActive());
		Group.setGroupAgeRange(groupDto.getGroupAgeRange());
		Group.setGroupDescription(groupDto.getGroupDescription());
		Group.setGroupEndDate(groupDto.getGroupEndDate());
		Group.setGroupMaster(groupDto.getGroupMaster());
		Group.setGroupMaxCount(groupDto.getGroupMaxCount());
		Group.setGroupName(groupDto.getGroupName());
		Group.setGroupPassword(groupDto.getGroupPassword());
		Group.setGroupRegion(groupDto.getGroupRegion());
		Group.setGroupStartDate(groupDto.getGroupStartDate());
		Group.setGroupStatus(groupDto.getGroupStatus());
		Group.setGroupWithGlobal(Group.getGroupWithGlobal());
		Group.setGroupWithChild(Group.getGroupWithChild());
		Group.setUpdatedTime(new Date());
		
		groupRepository.save(Group);
		
		return GroupMapper.MAPPER.toDto(Group);
	}
	
	//내 모임 조회
	public List<GroupMyListDto> getMyGroupList(String userId){
		List<GroupMyListDto> groupList=new ArrayList<>();
		UserEntity user = userRepository.findByUserId(userId);
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==user.getUserSeq())
				groupList.add(new GroupMyListDto(entity));
		}
		return groupList;
	}
	
	//모임 찾기
	public GroupEntity findGroup(int groupSeq) {
		GroupEntity findGroup = groupRepository.findByGroupSeq(groupSeq);
		 return findGroup;
	}
	
	//모임 정보 수정
	public GroupDto updateGroupImage(int groupSeq,String fileDownloadUri) {
		GroupEntity Group =groupRepository.findById(groupSeq).orElse(null);
		Group.setGroupImgUrl(fileDownloadUri);
		groupRepository.save(Group);
		return GroupMapper.MAPPER.toDto(Group);
	}

}
