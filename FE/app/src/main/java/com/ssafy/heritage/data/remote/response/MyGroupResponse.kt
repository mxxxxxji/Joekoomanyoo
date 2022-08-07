package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyGroupResponse(
    @SerializedName("groupSeq") var groupSeq: Int,
    @SerializedName("groupName") var groupName: String,
    @SerializedName("groupMaster") var groupMaster: String,
    @SerializedName("groupDescriaption") var groupDescriaption: String,
    @SerializedName("groupAccessType") var groupAccessType: String,
    @SerializedName("groupPassword") var groupPassword: String,
    @SerializedName("groupMaxCount") var groupMaxCount: Int,
    @SerializedName("groupRegion") var groupRegion: String,
    @SerializedName("groupStartDate") var groupStartDate: String,
    @SerializedName("groupEndDate") var groupEndDate: String,
    @SerializedName("groupAgeRange") var groupAgeRange: Int,
    @SerializedName("groupWithChild") var groupWithChild: String,
    @SerializedName("groupWithGlobal") var groupWithGlobal: String,
    @SerializedName("groupActive") var groupActive: String,
    @SerializedName("groupStatus") var groupStatus: String,
    @SerializedName("memberStatus") var memberStatus: Int,
    @SerializedName("memberIsEvaluated") var memberIsEvaluated: String
): Parcelable