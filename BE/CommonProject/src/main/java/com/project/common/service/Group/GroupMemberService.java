package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupBasicReqDto;
import com.project.common.dto.Group.GroupJoinReqDto;
import com.project.common.dto.Group.GroupMemberDto;
import com.project.common.dto.Group.GroupMemberListDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupMemberService{
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final GroupService groupService;

	//멤버 찾기(그륩 번호로)
	public List<GroupMemberEntity> findMember(long groupSeq) {
		List<GroupMemberEntity> findMember = new ArrayList<>();
		for(GroupMemberEntity entity: groupMemberRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findMember.add(entity);
			}
		}
		return findMember;
	}
	
	//모임 멤버 조회
	public List<GroupMemberDto> getMemberList(long groupSeq){
		List<GroupMemberDto> list = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupMemberDto(entity));
			}
		}
		return list;
	}
	
	//모임 참가
	@Transactional
	public void joinGroup(long groupSeq, GroupJoinReqDto groupJoinRequestDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
		group.addGroupMember(GroupMemberEntity.builder().memberAppeal(groupJoinRequestDto.getMemberAppeal()).userSeq(groupJoinRequestDto.getUserSeq()).build());
		groupRepository.save(group);
	}
	
	//모임 탈퇴
	@Transactional
	public void leaveGroup( GroupBasicReqDto groupLeave) {
		List<GroupMemberEntity> member = findMember(groupLeave.getGroupSeq());
		GroupEntity groupE = groupService.findGroup(groupLeave.getGroupSeq());
		for(GroupMemberEntity entity : member) {
			if(entity.getUserSeq()==groupLeave.getUserSeq()) {
				groupMemberRepository.deleteByUserSeq(groupLeave.getUserSeq());
				groupE.removeGroupMember(groupLeave.getUserSeq());
			}
		}
	}
	
	//모임 가입 승인
	public void approveMember(Long groupSeq,GroupBasicReqDto groupBasicReqDto) {
		for(GroupMemberEntity entity: findMember(groupSeq)) {
			if(entity.getUserSeq()==groupBasicReqDto.getUserSeq()) {
				entity.setMemberStatus(1);
				groupMemberRepository.save(entity);
				break;
			}
		}
	}
}
