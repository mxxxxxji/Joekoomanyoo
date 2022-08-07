package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	//일정 조회
	public List<GroupScheduleDto> getScheduleList(int groupSeq){
		List<GroupScheduleDto> list = new ArrayList<>();
		for(GroupScheduleEntity entity : groupScheduleRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupScheduleDto(entity));
			}
		}
		return list;
	}

	//내 모임 일정 조회
	public List<GroupScheduleDto> getMyScheduleList (int userSeq){
		List<GroupEntity> groupList= new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==userSeq) {
					groupList.add(entity.getGroup());
			}
		}
		List<GroupScheduleDto> scheduleList= new ArrayList<>();
		
		for(GroupEntity entity : groupList) {
			for(int i=0;i<entity.getSchedules().size();i++)
				scheduleList.add(new GroupScheduleDto(entity.getSchedules().get(i)));
		}
		return scheduleList;
	}
	
	//일정 등록
	@Transactional
	public String createGroupSchedule(int groupSeq, GroupScheduleDto gsDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
//		for(GroupScheduleEntity entity:group.getSchedules()) {
//			if(entity.getGsDateTime()==gsDto.getGsDateTime()) {
//				return "Fail";
//			}
//		}
		group.addGroupSchedule(GroupScheduleEntity.builder()
				.gsContent(gsDto.getGsContent())
				.gsDateTime(gsDto.getGsDateTime())
				.gsRegisteredAt(new Date())
				.gsUpdatedAt(new Date())
				.build());
		groupRepository.save(group);
		return "Success";
		
	}
	
	//일정 삭제
	@Transactional
	public String deleteGroupSchedule(int groupSeq, Date gsDateTime) {
		List<GroupScheduleEntity> schedules= findSchedule(groupSeq);
		System.out.println(groupSeq);
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupScheduleEntity entity : schedules) {
			if(entity.getGsDateTime()==gsDateTime) {
				System.out.println(entity.getGroup().getGroupSeq());
				groupScheduleRepository.deleteByGsDateTime(gsDateTime);
				group.removeGroupSchedule(gsDateTime);
			}
		}
		return "Success";
	}
	
	//일정 수정
	@Transactional
	public String modifyGroupSchedule(int groupSeq, GroupScheduleDto gsDto) {
		int cnt=0;
		for(GroupScheduleEntity entity: findSchedule(groupSeq)) {
			if(entity !=null && entity.getGsDateTime()==gsDto.getGsDateTime()) {
				entity.setGsContent(gsDto.getGsContent());
				entity.setGsUpdatedAt(new Date());
				groupScheduleRepository.save(entity);
				cnt++;break;
			}
		}
		if(cnt==0)
			return "Fail";
		return "Success";
	}
	
	//그륩 일정 찾기(그륩 번호로)
	public List<GroupScheduleEntity> findSchedule(int groupSeq) {
		List<GroupScheduleEntity> findSchedule = new ArrayList<>();
		for(GroupScheduleEntity entity: groupScheduleRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				findSchedule.add(entity);
			}
		}
		return findSchedule;
	}

}
