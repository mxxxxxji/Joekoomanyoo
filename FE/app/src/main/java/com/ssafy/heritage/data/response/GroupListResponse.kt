package com.ssafy.heritage.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class GroupListResponse(
    @SerializedName("group_seq") val groupSeq: Int,                     // 그룹번호
    @SerializedName("group_name") val groupName: String,                // 모임이름
    @SerializedName("group_maker") val groupMaker: Int,                 // 개설자
    @SerializedName("group_intro") val groupIntro: String,              //
    @SerializedName("group_access_type") val groupAccessType: Char,
    @SerializedName("group_pwd") val groupPwd: String,
    @SerializedName("group_isActive") val groupIsActive: Char,
    @SerializedName("group_isFull") val groupIsFull: Char,
    @SerializedName("group_created_at") val groupCreatedAt: String,
    @SerializedName("group_updated_at") val groupUpdatedAt: String,
    @SerializedName("attach_seq") val attachSeq: Int,

) : Parcelable