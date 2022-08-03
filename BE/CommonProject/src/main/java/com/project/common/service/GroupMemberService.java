package com.project.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.GroupJoinRequestDto;
import com.project.common.dto.GroupMemberDto;
import com.project.common.dto.GroupMemberListDto;
import com.project.common.dto.GroupMemberMapper;
import com.project.common.dto.UserDto;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupMemberEntity;
import com.project.common.entity.UserEntity;
import com.project.common.repository.GroupMemberRepository;
import com.project.common.repository.GroupRepository;
import com.project.common.repository.UserRepository;

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

	//참가자 목록 
	public List<GroupMemberListDto> getMemberList(long groupSeq){
		List<GroupMemberListDto> list = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupMemberListDto(entity));
			}
		}
		return list;
	}
//	//모임 참가
//	@Transactional
//	public void joinGroup(long groupSeq,String userId) {
//		GroupEntity group = groupService.findGroup(groupSeq);
//		UserEntity user = userRepository.findByUserId(userId);
//		group.addGroupMember(
//                groupMemberRepository.save(createGroupMember(group,user))
//        );
//	
//	}
//	//모임 참가
//	@Transactional
//	public void joinGroup(long groupSeq,UserDto userDto) {
//		GroupEntity group = groupService.findGroup(groupSeq);
//		UserEntity user = userDto.toEntity();
//		group.addGroupMember(
//                groupMemberRepository.save(createGroupMember(group,user))
//        );
//	
//	}
	
	@Transactional
	public void leaveGroup(long groupSeq,UserDto userDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
//		if (!groupMemberRepository.existsByUserAndStudy(user, party)) {
//      	throw new IllegalArgumentException("스터디에서 해당 사용자를 찾을 수 없습니다.");
//     }
		UserEntity user = userDto.toEntity();
		
	//	groupMemberRepository.deleteByGroup(user,group);
		//group.removeGroupMember(user);

  }
}
