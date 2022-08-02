package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ssafy.heritage.data.dto.GroupAttribute
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class GroupListResponse(
    @SerializedName("groupSeq") val groupSeq: Int=-1,                              // 그룹번호
    @SerializedName("attachSeq") val attachSeq: Int=-1,                            // 커버 이미지 첨부파일 번호
    @SerializedName("groupName") val groupName: String,                         // 모임이름
    @SerializedName("groupMaker") val groupMakerNickname: String,               // 개설자
    @SerializedName("groupDescription") val groupDescription: String,           // 모임 소개
    @SerializedName("groupAccessType") val groupAccessType: Char,               // 공개 설정 ('0' : 공개, '1': 비공개)
    @SerializedName("groupPassword") val groupPwd: String?="",                  // 비밀번호
    @SerializedName("groupIsActive") val groupIsActive: Char = 'Y',             // 그룹 활성화 여부 (N, Y), default = Y
    @SerializedName("groupStatus") val groupStatus: Char,                       // 그룹 상태 (R:모집중, O:모임시작 ,F:모임종료)
    @SerializedName("groupCreatedAt") val groupCreatedAt: String = "",               // 생성 시간
    @SerializedName("groupUpdatedAt") val groupUpdatedAt: String = "",               // 수정 시간
    @SerializedName("groupMaxCount") val groupMaxCount: Int,
    @SerializedName("region") var region: String,
    @SerializedName("age") var age: Int,
    @SerializedName("childJoin") var childJoin: Char,
    @SerializedName("globalJoin") var globalJoin: Char,
    @SerializedName("startDate") var startDate: String,
    @SerializedName("endDate") var endDate: String
) : Parcelable