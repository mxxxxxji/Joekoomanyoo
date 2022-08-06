package com.ssafy.heritage.data.dto

data class Member(
    val groupSeq: Int,
    var memberAppeal: String,
    var memberIsEvaluated: String,
    var memberStatus: Int,              // 0:신청자, 1:회원, 2:방장
    val memberSeq: Int,
    val userSeq: Int,
)
