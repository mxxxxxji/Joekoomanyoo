package com.ssafy.heritage.data.dto

data class Schedule(
    val myScheduleSeq: Int,
    val userSeq: Int,
    val myScheduleContent: String,
    val myScheduleDate: String,
    var myScheduleTime: String,
    val myScheduleRegistedAt: String,
    val myScheduleUpdatedAt: String,
)