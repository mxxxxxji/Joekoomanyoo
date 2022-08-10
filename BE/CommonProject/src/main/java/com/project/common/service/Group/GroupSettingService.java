package com.project.common.service.Group;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Group.GroupSettingDto;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.FirebaseCloudMessageService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupSettingService{
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	private final GroupMemberRepository groupMemberRepository;
	private final FirebaseCloudMessageService firebaseCloudMessageService;
	private final FcmTokenController fcmTokenController;
	
	
	//모임 상태 변경
	public String changeStatus(int groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findByGroupSeq(groupSeq);
		group.setGroupStatus(groupSettingDto.getGroupStatus());
		groupRepository.save(group);
		
		//FCM 알림
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if (entity.getGroup().getGroupSeq() == groupSeq) {
				UserEntity userEntity = userRepository.findByUserSeq(entity.getUserSeq());

				char status = groupSettingDto.getGroupStatus();
				if (status == 'O') {
					String fcmToken = userEntity.getFcmToken();
					String title = "모임 진행 알림";
					String body = userEntity.getUserNickname() + "님의 모임이 진행됩니다!";
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
				} else if (status == 'F') {
					String fcmToken = userEntity.getFcmToken();
					String title = "모임 종료 알림";
					String body = userEntity.getUserNickname() + "님의 모임이 종료됩니다.";
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
		}
		return "Success";
	}
	
	//모임 활성화 여부 변경
	public String changeActive(int groupSeq,GroupSettingDto groupSettingDto) {
		GroupEntity group =groupRepository.findByGroupSeq(groupSeq);
		group.setGroupActive(groupSettingDto.getGroupActive());
		groupRepository.save(group);
		return "Success";
	}
	
}
