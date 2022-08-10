package com.project.common.service;

import com.project.common.dto.User.UserDto;
import com.project.common.mapper.User.UserMapper;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Heritage.HeritageScrapRepository;
import com.project.common.repository.My.MyDailyMemoRepository;
import com.project.common.repository.My.MyScheduleRepository;
import com.project.common.repository.User.UserKeywordRepository;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MyDailyMemoRepository myDailyMemoRepository;


    // 회원가입
    @Transactional
    public boolean saveOrUpdateUser(UserDto userDto){
        UserEntity userEntity = UserMapper.MAPPER.toEntity(userDto);

        // 패스워스 암호화
        userEntity.encodePassword(this.passwordEncoder);

        if(userRepository.save(userEntity) != null){
            return true;
        }else{
            return false;
        }
    }


    // 회원 탈퇴
    @Transactional
    public boolean resignUser(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        // 만약 사용자가 존재하지 않거나 이미 삭제된 경우
        if(userEntity == null || userEntity.getIsDeleted()=='Y'){
            return false;
        }else{
            // 사용자가 존재하면 삭제표시
            userEntity.setIsDeleted('Y');
            userRepository.save(userEntity);

            int userSeq = userEntity.getUserSeq();
            // 탈퇴 처리 되면서 같이 바뀌는 것들
            // 문화유산 스크랩 목록 삭제
            heritageScrapRepository.deleteAllByUserSeq(userSeq);
            // 내 키워드 삭제
            userKeywordRepository.deleteAllByUserSeq(userSeq);
            // 나의 일정들 삭제
            myScheduleRepository.deleteAllByUserSeq(userSeq);
            // 데일리 메모 삭제
            myDailyMemoRepository.deleteAllByUserSeq(userSeq);
            return true;
        }
    }

    // 로그인
    @Transactional
    public UserDto login(Map<String,String> userInfo){
        UserEntity loginUser = userRepository.findByUserId(userInfo.get("userId"));
        UserDto userDto = UserMapper.MAPPER.toDto(loginUser);
        return userDto;
    }

    // 이메일 중복 검사
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
        // 만약 중복된 것이 없다면 닉네임 생성 가능
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
