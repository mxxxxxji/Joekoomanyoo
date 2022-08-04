package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ssafy.heritage.data.dto.GroupAttribute
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class GroupListResponse(
    @SerializedName("accessType") var accessType: Char,                 // 'O'
    @SerializedName("active") var active: Char,                         // 'Y'
    @SerializedName("ageRange") var ageRange: Int,                      // 나이대
    //@SerializedName("createdTime") val createdTime: LocalDateTime,
    @SerializedName("description") var description: String,
    @SerializedName("endDate") var endDate: Int,
    @SerializedName("groupSeq") var groupSeq: Int,
    @SerializedName("master") var master: String,
    @SerializedName("maxCount") var maxCount: Int,
    @SerializedName("name") var name: String,
    @SerializedName("password") var password: String,
    @SerializedName("region") var region: String,
    @SerializedName("startDate") var startDate: Int,
    @SerializedName("status") var status: Char,                          // 'R'
    @SerializedName("themaImg") var themaImg: String,
    //@SerializedName("updatedTime") val updatedTime: LocalDateTime,
    @SerializedName("withChild") var withChild: Char,                    // 'N'
    @SerializedName("withGlobal") var withGlobal: Char                   // 'N'
) : Parcelable