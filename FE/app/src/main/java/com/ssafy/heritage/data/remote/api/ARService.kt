package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.Stamp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ARService {

    // 전체 스탬프 리스트를 조회
    @GET("/api/ar/stamp/list")
    suspend fun selectAllStamp(): Response<List<Stamp>>

    // 나의 스탬프 리스트를 조회
    @GET("/api/mypage/stamp/{userSeq}")
    suspend fun getMyStamp(@Path("userSeq") userSeq: Int): Response<List<Stamp>>

    // 스탬프 나의 목록에 추가하기
    @POST("/api/ar/stamp/{userSeq}/{stampSeq}")
    suspend fun addStamp(@Path("userSeq") userSeq: Int, @Path("stampSeq") stampSeq: Int): Response<String>
}