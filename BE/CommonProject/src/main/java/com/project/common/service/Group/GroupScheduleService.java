package com.project.common.service.Group;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.service.FirebaseCloudMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupScheduleDto;
import com.project.common.dto.Group.Request.ReqGroupScheduleDto;
import com.project.common.dto.Group.Response.ResGroupScheduleDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Group.GroupScheduleEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Group.GroupScheduleRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupScheduleService{
	private final GroupRepository groupRepository;
	private final GroupScheduleRepository groupScheduleRepository;
	private final GroupService groupService;
	private final GroupMemberRepository groupMemberRepository;
	private final UserRepository userRepository;
	private final FirebaseCloudMessageService firebaseCloudMessageService;
	private final FcmTokenController fcmTokenController;
	//일정 조회
	public List<ResGroupScheduleDto> getScheduleList(int groupSeq){
		List<ResGroupScheduleDto> list = new ArrayList<>();
		for(GroupScheduleEntity entity : groupScheduleRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new ResGroupScheduleDto(entity));
			}
		}
		return list;
	}

	//내 모임 일정 조회
	public List<ResGroupScheduleDto> getMyScheduleList (String userId){
		List<GroupEntity> groupList= new ArrayList<>();
		UserEntity user = userRepository.findByUserId(userId);
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==user.getUserSeq()) {
					groupList.add(entity.getGroup());
			}
		}
		List<ResGroupScheduleDto> scheduleList= new ArrayList<>();
		
		for(GroupEntity entity : groupList) {
			for(int i=0;i<entity.getSchedules().size();i++)
				scheduleList.add(new ResGroupScheduleDto(entity.getSchedules().get(i)));
		}
		return scheduleList;
	}
	
	//일정 등록
	@Transactional
	public String createGroupSchedule(int groupSeq, ReqGroupScheduleDto gsDto) {
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
		
		
		//알림 받을 인원들
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if (entity.getGroup().getGroupSeq() == groupSeq) {
				UserEntity userEntity = userRepository.findByUserSeq(entity.getUserSeq());

				String fcmToken = userEntity.getFcmToken();
				String title = "일정 추가 알림";
				String body = userEntity.getUserNickname() + "님의 모임에서 일정이 추가되었습니다";
				try {
					firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				// 모임 기록하기
				FcmHistoryDto fcmHistoryDto = FcmHistoryDto.builder()
						.pushSeq(0)
						.userSeq(userEntity.getUserSeq())
						.pushTitle(title)
						.pushContent(body)
						.pushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.build();
				fcmTokenController.createHistory(fcmHistoryDto);
			}
		}
		return "Success";
		
	}
	
	//일정 삭제
	@Transactional
	public String deleteGroupSchedule(int groupSeq, Date gsDateTime) {
		List<GroupScheduleEntity> schedules= findSchedule(groupSeq);
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
	public String modifyGroupSchedule(int groupSeq, ReqGroupScheduleDto gsDto) {
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
		
		//알림 받을 인원들
		List<UserEntity> users = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll())
			if(entity.getGroup().getGroupSeq()==groupSeq)
				users.add(userRepository.findByUserSeq(entity.getUserSeq()));
		
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
