package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupJoinReqDto;
import com.project.common.dto.Group.GroupMemberListDto;
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
public class GroupMemberService{
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final GroupService groupService;
	private final UserRepository userRepository;

	//모임 멤버 조회
	public List<GroupMemberListDto> getMemberList(int groupSeq){
		List<GroupMemberListDto> list = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupMemberListDto(entity));
			}
		}
		if(list.size()==0)
			throw new IllegalArgumentException("등록된 멤버가 없습니다");
		return list;
	}
	
	//모임 참가
	@Transactional
	public String joinGroup(int groupSeq, GroupJoinReqDto requestDto) {
		for(GroupMemberEntity entity: groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==requestDto.getUserSeq()&&entity.getGroup().getGroupSeq()==groupSeq)
				throw new IllegalArgumentException("이미 참가 신청했습니다");
		}
		UserEntity user = userRepository.findByUserSeq(requestDto.getUserSeq());
	 	GroupEntity group = groupService.findGroup(groupSeq);
	 
	 	
		group.addGroupMember(GroupMemberEntity.builder()
				.memberAppeal(requestDto.getMemberAppeal())
				.userSeq(requestDto.getUserSeq())
				.memberStatus(0)
				.memberIsEvaluated('N')
				.createdTime(new Date())
				.updatedTime(new Date()).build());
		user.addGroup(group);
		groupRepository.save(group);
	 	userRepository.save(user);
		return "Success";
	}
	
	//모임 탈퇴
	@Transactional
	public String leaveGroup(int groupSeq, int userSeq) {
		List<GroupMemberEntity> member = findMember(groupSeq);
		GroupEntity group = groupService.findGroup(groupSeq);
		UserEntity user = userRepository.findByUserSeq(userSeq);
		for(GroupMemberEntity entity : member) {
			if(entity.getUserSeq()==userSeq) {
				groupMemberRepository.deleteByUserSeq(userSeq);
				group.removeGroupMember(userSeq);
				user.removeGroup(groupSeq);
			}
		}
		return "Success";
	}
	
	//모임 가입 승인
	public String approveMember(int groupSeq, int userSeq) {
		for(GroupMemberEntity entity: findMember(groupSeq)) {
			if(entity.getUserSeq()==userSeq) {
				entity.setMemberStatus(1);
				entity.setApproveTime(new Date());
				entity.setUpdatedTime(new Date());
				groupMemberRepository.save(entity);
				break;
			}
		}
		return "Success";
	}
	
	//멤버 찾기(그륩 번호로)
	public List<GroupMemberEntity> findMember(int groupSeq) {
		List<GroupMemberEntity> findMember = new ArrayList<>();
		for(GroupMemberEntity entity: groupMemberRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findMember.add(entity);
			}
		}
		return findMember;
	}
}
