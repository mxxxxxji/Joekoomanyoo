package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyGroupResponse(
    @SerializedName( "groupName") var groupName: String,
    @SerializedName( "master") var master: String,
    @SerializedName( "descriaption") var descriaption: String,
    @SerializedName( "accessType") var accessType: Char,
    @SerializedName( "password") var password: String,
    @SerializedName( "maxCount") var maxCount: Int,
    @SerializedName( "region") var region: String,
    @SerializedName( "startDate") var startDate: Int,
    @SerializedName( "endDate") var endDate: Int,
    @SerializedName( "ageRange") var ageRange: Int,
    @SerializedName("withChild") var withChild: Char,
    @SerializedName("withGlobal") var withGlobal: Char,
    @SerializedName("active") var active: Char,
    @SerializedName("status") var status: Char,
    @SerializedName("memberStatus") var memberStatus: Int,
    @SerializedName("memberIsEvaluated") var memberIsEvaluated: Char
): Parcelable