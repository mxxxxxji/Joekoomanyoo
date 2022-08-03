package com.project.common.dto;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Api("회원가입 사용자 정보")
public class UserSignDto {
    private int userSeq;
    private String userId;
    private String userNickname;
    private String userPassword;
    private String userBirth;
    private char userGender;
    private String socialLoginType;
    private char isDeleted;
}
