package com.project.common.service.Group;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Group.Response.ResGroupDestinationDto;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.service.FirebaseCloudMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupDestinationRepository;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Heritage.HeritageRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDestinationService{

	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;
	private final HeritageRepository heritageRepository;
	private final GroupDestinationRepository groupDestinationRepository;
	private final GroupService groupService;
	private final UserRepository userRepository;
	private final FirebaseCloudMessageService firebaseCloudMessageService;
	private final FcmTokenController fcmTokenController;
	

	//내 모임 목적지 조회
	public List<ResGroupDestinationDto> getMyDestinationList(String userId){
		List<GroupEntity> groupList= new ArrayList<>();
		UserEntity user = userRepository.findByUserId(userId);
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==user.getUserSeq()) {
					groupList.add(entity.getGroup());
				}
			}
			List<ResGroupDestinationDto> destinationList= new ArrayList<>();
			
			for(GroupEntity entity : groupList) {
				for(int i=0;i<entity.getDestinations().size();i++) {
					HeritageEntity heritage=heritageRepository.findByHeritageSeq(entity.getDestinations().get(i).getHeritageSeq());
					if(heritage!=null)
						destinationList.add(new ResGroupDestinationDto(entity.getDestinations().get(i),heritage));
				}
			}
			return destinationList;
	}
	
	//모임 목적지 조회
	public List<ResGroupDestinationDto> getGroupDestinationList(int groupSeq){
		List<ResGroupDestinationDto> list = new ArrayList<>();
		HeritageEntity herritage;
		for(GroupDestinationEntity entity : groupDestinationRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				herritage=heritageRepository.findByHeritageSeq(entity.getHeritageSeq());
				if(herritage!=null)

					list.add(new ResGroupDestinationDto(entity,herritage));
			}
		}
		return list;
	}
	
	//모임 목적지 추가
	@Transactional
	public String addGroupDestination(int groupSeq, int heritageSeq) {
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDestinationEntity entity:group.getDestinations()) {
			if(entity!=null&&entity.getHeritageSeq()==heritageSeq) {
				return "Fail";
			}
		}
		group.addGroupDestination(GroupDestinationEntity.builder().gdCompleted('N').heritageSeq(heritageSeq).build());
		groupRepository.save(group);
		
		//알림 받을 인원들
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if (entity.getGroup().getGroupSeq() == groupSeq) {
				UserEntity userEntity = userRepository.findByUserSeq(entity.getUserSeq());

				String fcmToken = userEntity.getFcmToken();
				String title = "목적지 추가 알림";
				String body = userEntity.getUserNickname() + "님의 모임에서 목적지가 추가되었습니다";
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
	
	//모임 목적지 삭제
	@Transactional
	public String deleteGroupDestination(int groupSeq, int heritageSeq) {
		List<GroupDestinationEntity> destinations= findDestination(groupSeq);
		if(destinations==null) 
			return "Fail";
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDestinationEntity entity : destinations) {
			if(entity.getHeritageSeq()==heritageSeq) {
				groupDestinationRepository.deleteByHeritageSeq(heritageSeq);
				group.removeGroupDestination(heritageSeq);
			}
		}
		return "Success";
	}
	
	//모임 목적지 완료 표시
	@Transactional
	public String modifyGroupDestination(int groupSeq,int heritageSeq) {
		for(GroupDestinationEntity entity: findDestination(groupSeq)) {
			if(entity !=null && entity.getHeritageSeq()==heritageSeq) {
				entity.setGdCompleted('Y');
				groupDestinationRepository.save(entity);
				return "Success";
			}
		}
		return "Fail";
	}
	
	//해당 모임 메모 찾기
	public List<GroupDestinationEntity> findDestination(int groupSeq) {
		List<GroupDestinationEntity> findDestination = new ArrayList<>();
		for(GroupDestinationEntity entity: groupDestinationRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findDestination.add(entity);
			}
		}
		return findDestination;
	}

}
