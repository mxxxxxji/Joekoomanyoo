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
				list.add(new GroupMemberListDto(entity,userRepository.findByUserSeq(entity.getUserSeq())));
			}
		}
		return list;
	}
	
	// 모임 참가
	@Transactional
	public String joinGroup(int groupSeq, GroupJoinReqDto requestDto) {
		int masterUserSeq=0;
		for(GroupMemberEntity entity: groupMemberRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq&&entity.getMemberStatus()==2) {
				masterUserSeq=entity.getUserSeq();
			}
			if(entity.getUserSeq()==requestDto.getUserSeq()&&entity.getGroup().getGroupSeq()==groupSeq)
				return "Fail";
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
	 	//모임장
		UserEntity master = userRepository.findByUserSeq(masterUserSeq);
	 	
		return "Success";
	}
	
	//모임 탈퇴
	@Transactional
	public String leaveGroup(int groupSeq,String userId) {
		List<GroupMemberEntity> member = findMember(groupSeq);
		GroupEntity group = groupService.findGroup(groupSeq);
		UserEntity user = userRepository.findByUserId(userId);
		for(GroupMemberEntity entity : member) {
			if(entity.getUserSeq()==user.getUserSeq()) {
				groupMemberRepository.deleteByUserSeq(user.getUserSeq());
				group.removeGroupMember(user.getUserSeq());
				user.removeGroup(groupSeq);
			}
		}
	 	//탈퇴인원
		UserEntity leave = userRepository.findByUserId(userId);
		
		
		return "Success";
	}
	
	//모임 가입 승인
	public String approveMember(int groupSeq, String userId) {
		UserEntity user = userRepository.findByUserId(userId);
		for(GroupMemberEntity entity: findMember(groupSeq)) {
			if(entity.getUserSeq()==user.getUserSeq()) {
				entity.setMemberStatus(1);
				entity.setApproveTime(new Date());
				entity.setUpdatedTime(new Date());
				groupMemberRepository.save(entity);
				break;
			}
		}
		//승인인원
		UserEntity approve = userRepository.findByUserId(userId);
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
