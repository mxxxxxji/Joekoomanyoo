package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class GroupListResponse(
    @SerializedName("group_seq") val groupSeq: Int,                     // 그룹번호
    @SerializedName("group_name") val groupName: String,                // 모임이름
    @SerializedName("group_maker") val groupMaker: Int,                 // 개설자
    @SerializedName("group_intro") val groupIntro: String,              // 모임 소개
    @SerializedName("group_access_type") val groupAccessType: Char,     // 공개 설정
    @SerializedName("group_pwd") val groupPwd: String,                  // 비밀번호
    @SerializedName("group_isActive") val groupIsActive: Char,          // 활성화 여부
    @SerializedName("group_isFull") val groupIsFull: Char,              // 모집 여부
    @SerializedName("group_created_at") val groupCreatedAt: String,     // 생성 시간
    @SerializedName("group_updated_at") val groupUpdatedAt: String,     // 수정 시간
    @SerializedName("attach_seq") val attachSeq: Int,                   // 커버 이미지 첨부파일 번호

    @SerializedName("ga_seq") val gaSeq: Int,                           // 모임 속성 인덱스 번호
    @SerializedName("ga_max") val gaMax: Int,                           // 최대 인원
    @SerializedName("ga_region") val gaRegion: String,                  // 지역
    @SerializedName("ga_start_date") val gaStartDate: String,           // 일정 시작일
    @SerializedName("ga_end_date") val gaEndDate: String,               // 일정 종료일
    @SerializedName("ga_child_join") val gaChildJoin: Char,             // 아이 동반 여부
    @SerializedName("ga_global_join") val gaGlobalJoin: Char,           // 글로벌 모임 여부
    @SerializedName("ga_age") val gaAge: Int,                           // 나이대
    @SerializedName("ga_created_at") val gaCreatedAt: String,           // 생성시간
    @SerializedName("ga_updated_at") val gaUpdatedAt: String            // 수정시간
) : Parcelable