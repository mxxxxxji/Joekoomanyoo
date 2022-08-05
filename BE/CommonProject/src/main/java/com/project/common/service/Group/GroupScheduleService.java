package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupBasicReqDto;
import com.project.common.dto.Group.GroupDto;
import com.project.common.dto.Group.GroupMyListDto;
import com.project.common.dto.Group.GroupScheduleDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Group.GroupScheduleEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Group.GroupScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupScheduleService{
	private final GroupRepository groupRepository;
	private final GroupScheduleRepository groupScheduleRepository;
	private final GroupService groupService;

	private final GroupMemberRepository groupMemberRepository;
	
	//그륩 일정 찾기(그륩 번호로)
	public List<GroupScheduleEntity> findSchedule(long groupSeq) {
		List<GroupScheduleEntity> findSchedule = new ArrayList<>();
		for(GroupScheduleEntity entity: groupScheduleRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				findSchedule.add(entity);
			}
		}
		return findSchedule;
	}
	
	//일정 조회
	public List<GroupScheduleDto> getScheduleList(long groupSeq){
		List<GroupScheduleDto> list = new ArrayList<>();
		for(GroupScheduleEntity entity : groupScheduleRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupScheduleDto(entity));
			}
		}
		if(list.size()==0)
			throw new IllegalArgumentException("등록된 일정이 없습니다");
		return list;
	}
//	
//	//내 모임 일정 조회
//	public List<GroupScheduleDto> getMyGroupList(long userSeq, GroupBasicReqDto Dto){
//		List<GroupDto> myGroupList=new ArrayList<>();
//		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
//			if(entity.getUserSeq()==userSeq) {
//				myGroupList.ad
//			}
//		}
//		
//		for(GroupScheduleEntity entity : groupScheduleRepository.findAll()) {
//			if(entity.getGroup().getGroupSeq()==groupSeq)
//				groupList.add();
//		}
//		return groupList;
//	}
//	
	//일정 등록
	@Transactional
	public GroupScheduleDto createGroupSchedule(long groupSeq, GroupScheduleDto gsDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupScheduleEntity entity:group.getSchedules()) {
			if(entity.getGsDateTime()==gsDto.getGsDateTime()) {
				throw new IllegalArgumentException("같은 시간에 등록한 일정이 있습니다");
			}
		}
		group.addGroupSchedule(GroupScheduleEntity.builder().gsContent(gsDto.getGsContent()).gsDateTime(gsDto.getGsDateTime()).build());
		groupRepository.save(group);
		return gsDto;
	}
	
	//일정 삭제
	@Transactional
	public void deleteGroupSchedule(long groupSeq, GroupScheduleDto gsDto) {
		List<GroupScheduleEntity> schedules= findSchedule(groupSeq);
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupScheduleEntity entity : schedules) {
			if(entity.getGsDateTime()==gsDto.getGsDateTime()) {
				groupScheduleRepository.deleteByGsDateTime(gsDto.getGsDateTime());
				group.removeGroupSchedule(gsDto.getGsDateTime());
			}
		}
	}
	
	
	
	//일정 수정
	@Transactional
	public GroupScheduleDto modifyGroupSchedule(long groupSeq, GroupScheduleDto gsDto) {
		for(GroupScheduleEntity entity: findSchedule(groupSeq)) {
			if(entity !=null && entity.getGsDateTime()==gsDto.getGsDateTime()) {
				entity.setGsContent(gsDto.getGsContent());
				groupScheduleRepository.save(entity);
				break;
			}
		}
		return gsDto;
	}

}
