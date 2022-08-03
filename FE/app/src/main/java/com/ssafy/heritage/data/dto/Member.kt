package com.ssafy.heritage.data.dto

import android.provider.ContactsContract

data class Member(
    val groupSeq: Int,
    var memberAppeal: String,
    var memberIsEvaluated: String,
    var memberStatus: Int,
    val userNickname: Int
)
