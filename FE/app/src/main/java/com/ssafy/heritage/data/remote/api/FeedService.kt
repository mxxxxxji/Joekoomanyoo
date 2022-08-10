package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.data.remote.response.FeedListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedService {
    // 피드 API
    // 내 피드 조회
    @GET("/api/feed/{feedSeq}/active/{feedOpen}")
    suspend fun selectMyFeeds(): Response<List<FeedListResponse>>

    // 피드 조회 by 해시태그
    @GET("/api/feed/list-by-hashtag/{fhTag}")
    suspend fun selectFeedsByHashtag(@Path("fhTag") fhTag: String): Response<List<FeedListResponse>>

    // 피드 전체 조회
    @GET("/api/feed/list")
    suspend fun selectAllFeeds(): Response<List<FeedListResponse>>

    // 피드 등록
    @POST("/api/feed/add")
    suspend fun insertFeed(@Body body: FeedAddRequest): Response<FeedListResponse>

    // 피드 수정
    // 피드 보기
    // 피드 삭제
    // 피드 활성화 여부 (Y: 공개, N: 비공개)

    // 피드 좋아요 API
    // 피드 좋아요 등록
    // 피드 좋아요 개수 조회
    // 피드 좋아요 해제

    // 피드 해시태그 API
    // 피드 해시태그 등록
    // 피드 해시태그 목록 조회
    // 피드 해시태그 삭제
}