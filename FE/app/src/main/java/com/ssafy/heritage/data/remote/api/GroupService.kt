package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.GroupAttribute
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.remote.request.GroupAddRequest
import com.ssafy.heritage.data.remote.request.GroupBasic
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import retrofit2.Response
import retrofit2.http.*

interface GroupService {

    //모임 관리 API
    // 새로운 모임을 개설한다
    @POST("/api/group/add/{userSeq}")
    suspend fun insertGroup(@Path("userSeq") userSeq: Int, @Body body: GroupAddRequest): Response<GroupListResponse>

    // 모임 목록을 조회한다
    @GET("/api/group/list")
    suspend fun selectAllGroups(): Response<List<GroupListResponse>>

    // 모임을 삭제한다
    @DELETE("/api/group/{groupSeq}/delete")
    suspend fun deleteGroup(@Path("groupSeq") groupSeq: Int): Response<Boolean>

    // 모임 정보를 수정한다
    @PUT("/api/group/{groupSeq}/update")
    suspend fun modifyGroup(@Path("groupSeq") groupSeq: Int, @Body body: GroupListResponse): Response<GroupListResponse>

    // 모임 정보를 조회한다
    @GET("/api/group/{groupSeq}/info")
    suspend fun selectGroupDetail(@Path("groupSeq") groupSeq: Int): Response<GroupListResponse>

    // 나의 모임 정보만 조회한다
    @GET("/api/group/my-group/{userSeq}")
    suspend fun selectMyGroups(@Path("userSeq") userSeq: Int): Response<List<MyGroupResponse>>








    // 모임 활성화 여부를 등록한다
    @PUT("/api/group/{groupSeq}/active")
    suspend fun changeGroupActiveState(@Body body: Int): Response<Boolean>

    // 가입을 승인한다
    @PUT("/api/group/{groupSeq}/member/approve")
    suspend fun approveGroupJoin(@Path("groupSeq") groupSeq: Int,@Body body: GroupBasic): Response<Boolean>

    // 가입을 신청한다
    @POST("/api/group/{groupSeq}/member/join")
    suspend fun applyGroupJoin(@Path("groupSeq") groupSeq: Int, @Body body: GroupJoin): Response<Boolean>

    // 가입을 탈퇴/취소/거절(방장)/취소한다
    @DELETE("/api/group/{groupSeq}/member/leave")
    suspend fun leaveGroupJoin(@Path("groupSeq") groupSeq: Int, @Query("userSeq") userSeq: Int): Response<Boolean>

    // 회원 목록을 조회한다
    @GET("/api/group/{groupSeq}/member/list")
    suspend fun selectGroupMembers(@Path("groupSeq") groupSeq: Int): Response<List<Member>>



    // 회원 프로필을 조회한다

    // 회원 평가를 조회한다

    // 회원을 평가한다

    // 모임 일정을 등록한다

    // 모임 일정을 수정한다

    // 모임 일정을 삭제한다

    // 모임 일정을 조회한다

    // 모임 데일리 메모를 등록한다

    // 모임 데일리 메모를 조회한다

    // 모임 데일리 메모를 수정한다

    // 모임 데일리 메모를 삭제한다

    // 모임 목적지를 등록한다
    @POST("/api/group/{groupSeq}/destination/add")
    suspend fun insertGroupDestination(@Path("groupSeq") groupSeq: Int, @Body heritageSeq: Int): Response<String>

    // 모임 목적지를 삭제한다

    // 모임 목적지 방문 여부를 등록한다

    // 채팅을 불러온다

    // 채팅을 입력한다

    // 채팅을 삭제한다

}