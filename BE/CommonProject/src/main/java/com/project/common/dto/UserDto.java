package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.project.common.entity.GroupEntity;
import com.project.common.entity.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int userSeq;
    private String userId;
    private String userNickname;
    private String userPassword;
    private String userBirth;
    private char userGender;
    private String socialLoginType;
    private String profileImgUrl;
    private String fcmToken;
    private Date userRegistedAt;
    private Date userUpdatedAt;
    private char isDeleted;

    
    public UserEntity toEntity(){
        return UserEntity.builder()
                .userSeq(userSeq)
                .userId(userId)
                .userNickname(userNickname)
                .userPassword(userPassword)
                .userBirth(userBirth).build();

    }
}
