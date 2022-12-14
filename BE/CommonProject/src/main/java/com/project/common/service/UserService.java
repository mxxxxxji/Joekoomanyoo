package com.project.common.service;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.dto.User.UserDto;
import com.project.common.mapper.User.UserMapper;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Feed.FeedRepository;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Heritage.HeritageScrapRepository;
import com.project.common.repository.My.MyDailyMemoRepository;
import com.project.common.repository.My.MyScheduleRepository;
import com.project.common.repository.User.UserKeywordRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.Feed.FeedService;
import com.project.common.service.Group.GroupMemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HeritageScrapRepository heritageScrapRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final MyScheduleRepository myScheduleRepository;
    private final FeedService feedService;
    private final FeedRepository feedRepository;

    private final GroupRepository groupRepository;
    private final GroupMemberService groupMemberService;
    private final GroupMemberRepository groupMemberRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmTokenController fcmTokenController;

    // ????????????
    @Transactional
    public boolean saveOrUpdateUser(UserDto userDto){
        UserEntity userEntity = UserMapper.MAPPER.toEntity(userDto);

        // ???????????? ?????????
        userEntity.encodePassword(this.passwordEncoder);

        if(userRepository.save(userEntity) != null){
            return true;
        }else{
            return false;
        }
    }


    // ?????? ??????
    @Transactional
    public boolean resignUser(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        // ?????? ???????????? ???????????? ????????? ?????? ????????? ??????
        if(userEntity == null || userEntity.getIsDeleted()=='Y'){
            return false;
        }else{
            // ???????????? ???????????? ????????????
            userEntity.setIsDeleted('Y');
            userRepository.save(userEntity);
            
          
            //?????? ??????
            List<FeedEntity> feeds =new ArrayList<>();
            for(FeedEntity entity : feedRepository.findAll()) 
            	if(entity.getUser().getUserSeq()==userEntity.getUserSeq()) 
            		feeds.add(entity);
            	        
            for(int i=0; i<feeds.size();i++) 
            	feedService.deleteFeed(userId, feeds.get(i).getFeedSeq());
                    
            // ?????? ??????
            for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
            	if(entity.getUserSeq()==userEntity.getUserSeq()) {
            		if(entity.getMemberStatus()==2) {
            			entity.getGroup().setGroupActive('N');
            			entity.getGroup().setGroupStatus('F');
            			entity.setMemberStatus(1);
            			groupRepository.save(entity.getGroup());
            		}
            		groupMemberService.leaveGroup(entity.getGroup().getGroupSeq(), userId);
            	}

                //????????? ??????
                UserEntity user = userRepository.findByUserSeq(entity.getUserSeq());
                String fcmToken = user.getFcmToken();
                String title = "?????? ?????? ??????";
                String body =  user.getUserNickname() + " ?????? "+ entity.getGroup().getGroupName() + " ???????????? ????????? ?????? ????????? ???????????????????????????.";
                try {
                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // ?????? ????????????
                FcmHistoryDto fcmHistoryDto = FcmHistoryDto.builder()
                        .pushSeq(0)
                        .userSeq(user.getUserSeq())
                        .pushTitle(title)
                        .pushContent(body)
                        .pushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build();
                fcmTokenController.createHistory(fcmHistoryDto);
            }
            
            int userSeq = userEntity.getUserSeq();
            // ?????? ?????? ????????? ?????? ????????? ??????
            // ???????????? ????????? ?????? ??????
            heritageScrapRepository.deleteAllByUserSeq(userSeq);
            // ??? ????????? ??????
            userKeywordRepository.deleteAllByUserSeq(userSeq);
            // ?????? ????????? ??????
            myScheduleRepository.deleteAllByUserSeq(userSeq);
            return true;
        }
    }

    // ?????????
    @Transactional
    public UserDto login(Map<String,String> userInfo){
        UserEntity loginUser = userRepository.findByUserId(userInfo.get("userId"));
        UserDto userDto = UserMapper.MAPPER.toDto(loginUser);
        return userDto;
    }

    // ????????? ?????? ??????
    @Transactional
    public boolean checkEmail(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null){
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public boolean checkNickname(String userNickname) {
        // ?????? ????????? ?????? ????????? ????????? ?????? ??????
        if(userRepository.findByUserNickname(userNickname) == null){
            return true;
        }else{
            return false;
        }
    }


    @Transactional
    public UserDto find(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        return UserMapper.MAPPER.toDto(userEntity);
    }
}
