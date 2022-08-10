package com.ssafy.heritage.data.dto

data class Stamp(
    val stampSeq: Int = 0,
    val stampImgUrl: String = "",
    val stampTitle: String = "",
    val stampText: String = "",
    val heritageSeq: Int = 0,
    val heritageLocal: String = "",
    val heritageLng: String = "",
    val heritageLat: String = "",
    val stampCategory: String = "",
    var found: Char = 'N'
)