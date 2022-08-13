package com.ssafy.heritage.util

import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "DateFormatter___"

object DateFormatter {

    fun String.formatChatDate(): String {

        val list = this.split(" ")
        val s = list[0] + " " + list[1].split(".")[0] //"2022-08-13 01:18:10"
        val dt = SimpleDateFormat("yyyyy-mm-dd hh:mm:ss")
        val date = dt.parse(s)

        val format = SimpleDateFormat("yyyy-MM-dd (E)\nk시 m분", Locale.KOREAN)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        return format.format(date.time)
    }
}