package com.project.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.GroupJoinReqDto;
import com.project.common.dto.GroupMapper;
import com.project.common.dto.GroupMemberDto;
import com.project.common.dto.GroupBasicReqDto;
import com.project.common.dto.GroupDto;
import com.project.common.dto.GroupMemberListDto;
import com.project.common.dto.GroupMemberMapper;
import com.project.common.dto.GroupSettingDto;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupMemberEntity;
import com.project.common.repository.GroupMemberRepository;
import com.project.common.repository.GroupRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupMemberService{
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final UserService userService;
	private final GroupService groupService;

	
	//그륩 찾기(그륩 번호로)
	public List<GroupMemberEntity> findGroup(long groupSeq) {
		List<GroupMemberEntity> findGroup = new ArrayList<>();
		for(GroupMemberEntity entity: groupMemberRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findGroup.add(entity);
			}
		}
		return findGroup;
	}
	
	//가입자 목록 목록 
	public List<GroupMemberListDto> getMemberList(long groupSeq){
		List<GroupMemberListDto> list = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupMemberListDto(entity));
			}
		}
		return list;
	}
	
	//그륩 참가
	@Transactional
	public void joinGroup(long groupSeq, GroupJoinReqDto groupJoinRequestDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
		group.addGroupMember(GroupMemberEntity.builder().memberAppeal(groupJoinRequestDto.getMemberAppeal()).userSeq(groupJoinRequestDto.getUserSeq()).build());
		groupRepository.save(group);
	}
	
	//그륩 탈퇴
	@Transactional
	public void leaveGroup( GroupBasicReqDto groupLeave) {
		List<GroupMemberEntity> group = findGroup(groupLeave.getGroupSeq());
		GroupEntity groupE = groupService.findGroup(groupLeave.getGroupSeq());
		for(GroupMemberEntity entity : group) {
				groupMemberRepository.deleteByUserSeq(groupLeave.getUserSeq());
				groupE.removeGroupMember(groupLeave.getUserSeq());
		}
	}
	
	//모임 수락
	public void approveMember(Long groupSeq,GroupBasicReqDto groupBasicReqDto) {
		for(GroupMemberEntity entity: findGroup(groupSeq)) {
			if(entity.getUserSeq()==groupBasicReqDto.getUserSeq()) {
				entity.setMemberStatus(1);
				groupMemberRepository.save(entity);
				break;
			}
		}
	}
}
