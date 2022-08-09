package com.ssafy.heritage.data.dto

data class Stamp(
    val stampSeq: Int = 0,
    val stampImgUrl: String = "",
    val stampTitle: String = "",
    val stampText: String = "",
    val heritageSeq: Int = 0,
    var found: Char = 'N'
)