package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ssafy.heritage.data.dto.GroupAttribute
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class GroupListResponse(
    //@SerializedName("attachSeq") val attachSeq: Int,
    @SerializedName("groupMaker") val groupMakerNickname: String,               // 개설자
    @SerializedName("groupName") val groupName: String,                         // 모임이름
    @SerializedName("groupDescription") val groupDescription: String,           // 모임 소개
    @SerializedName("groupAccessType") val groupAccessType: Char,               // 공개 설정 ('0' : 공개, '1': 비공개)
    @SerializedName("groupPassword") val groupPassword: String?="",                  // 비밀번호
    @SerializedName("groupStatus") val groupStatus: Char,                       // 그룹 상태 (R:모집중, O:모임시작 ,F:모임종료)
    @SerializedName("groupIsActive") val groupIsActive: Char = 'Y',             // 그룹 활성화 여부 (N, Y), default = Y
    @SerializedName("groupTotalCount") val groupMaxCount: Int,
    @SerializedName("groupRegion") var region: String,
    @SerializedName("groupStartDate") var startDate: Int,
    @SerializedName("groupEndDate") var endDate: Int,
    @SerializedName("groupAgeRange") var age: Int,
    @SerializedName("groupChild") var childJoin: Char,
    @SerializedName("groupGlobal") var globalJoin: Char,
    @SerializedName("groupSeq") val groupSeq: Int

    @SerializedName(accessType)": "string",
@SerializedName(active)": "string",
    @SerializedName(ageRange)": 0,
        @SerializedName(createdTime)": "2022-08-04T03:36:30.899Z",
        @SerializedName(description": "string",
            @SerializedName(endDate": 0,
                @SerializedName(groupSeq": 0,
                    @SerializedName(master": "string",
                        @SerializedName(maxCount": 0,
                            @SerializedName(name": "string",
                                @SerializedName(password": "string",
                                    @SerializedName(region": "string",
                                        @SerializedName(startDate": 0,
                                            @SerializedName(status": "string",
                                                @SerializedName(themaImg": "string",
                                                    @SerializedName(updatedTime": "2022-08-04T03:36:30.899Z",
                                                    @SerializedName(withChild": "string",
                                            @SerializedName(withGlobal)": "string"
) : Parcelable