package com.project.common.service.Group;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Group.Request.ReqGroupJoinDto;
import com.project.common.dto.Group.Response.ResGroupMemberDto;
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
public class GroupMemberService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
	private final FcmTokenController fcmTokenController;

    //모임 멤버 조회
    public List<ResGroupMemberDto> getMemberList(int groupSeq) {
        List<ResGroupMemberDto> list = new ArrayList<>();
        for(GroupMemberEntity entity: groupRepository.findByGroupSeq(groupSeq).getMembers()) {
            list.add(new ResGroupMemberDto(entity, userRepository.findByUserSeq(entity.getUserSeq())));
        }
        return list;
    }

    // 모임 참가
    @Transactional
    public String joinGroup(int groupSeq, ReqGroupJoinDto requestDto) {
        int masterUserSeq = 0;
        GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
        if(group==null)
        	return "Fail - No Group";
        for (GroupMemberEntity entity : group.getMembers()) {
        	if(entity.getMemberStatus()==2) {
        		masterUserSeq=entity.getUserSeq();// 방장 userSeq 추출
        	}
        	if(entity.getUserSeq()==requestDto.getUserSeq()) {
        		return "Fail - Already Reigstered";        		
        	}
        }
        
        UserEntity user = userRepository.findByUserSeq(requestDto.getUserSeq());
        
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

        //FCM 알림
        UserEntity master = userRepository.findByUserSeq(masterUserSeq);
        String fcmToken = master.getFcmToken();
        String title = "모임 신청 알림";
        String body = master.getUserNickname() + "님의 모임에 가입 신청이 들어왔습니다.";
        try {
            	firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

		// 모임 기록하기
		FcmHistoryDto fcmHistoryDto = FcmHistoryDto.builder()
				.pushSeq(0)
				.userSeq(master.getUserSeq())
				.pushTitle(title)
				.pushContent(body)
				.pushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.build();
		fcmTokenController.createHistory(fcmHistoryDto);

        return "Success";
    }

    //모임 탈퇴
    @Transactional
    public String leaveGroup(int groupSeq, String userId) {
        GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
        if(group==null)
        	return "Fail- No group";
        UserEntity user = userRepository.findByUserId(userId);
        for (GroupMemberEntity entity : group.getMembers()) {
            if (entity.getUserSeq() == user.getUserSeq()) {
                groupMemberRepository.deleteByMemberSeq(entity.getMemberSeq());
                group.removeGroupMember(entity.getMemberSeq());
                user.removeGroup(groupSeq);
            }
        }
        
        //FCM 알림
        UserEntity leave = userRepository.findByUserId(userId);
        String fcmToken = leave.getFcmToken();
        String title = "모임 제외 알림";
        String body = "죄송합니다. " + leave.getUserNickname() + "님은 모임에서 제외되셨습니다.";
        try {
            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 모임 기록하기
        FcmHistoryDto fcmHistoryDto = FcmHistoryDto.builder()
                .pushSeq(0)
                .userSeq(leave.getUserSeq())
                .pushTitle(title)
                .pushContent(body)
                .pushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        fcmTokenController.createHistory(fcmHistoryDto);

        return "Success";
    }

    //모임 가입 승인
    public String approveMember(int groupSeq, String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        GroupEntity group=groupRepository.findByGroupSeq(groupSeq);
        if(group==null)
        	return "Fail - No group";
        for (GroupMemberEntity entity : group.getMembers()) {
            if (entity.getUserSeq() == user.getUserSeq()) {
                entity.setMemberStatus(1);
                entity.setApproveTime(new Date());
                entity.setUpdatedTime(new Date());
                groupMemberRepository.save(entity);
                break;
            }
        }
        
        //FCM 알림
        UserEntity approve = userRepository.findByUserId(userId);
        String fcmToken = approve.getFcmToken();
        String title = "모임 가입 알림";
        String body = "축하드립니다! " + approve.getUserNickname() + "님은 모임에 가입되셨습니다.";
        try {
            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 모임 기록하기
        FcmHistoryDto fcmHistoryDto = FcmHistoryDto.builder()
                .pushSeq(0)
                .userSeq(approve.getUserSeq())
                .pushTitle(title)
                .pushContent(body)
                .pushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        fcmTokenController.createHistory(fcmHistoryDto);

        return "Success";
    }

}
