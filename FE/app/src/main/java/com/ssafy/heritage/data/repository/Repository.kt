package com.ssafy.heritage.data.repository

import android.content.Context
import com.ssafy.heritage.data.dto.*
import com.ssafy.heritage.data.remote.api.RetrofitInstance.feedApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.fileApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.groupApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.heritageApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.userApi
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.data.remote.request.GroupAddRequest
import com.ssafy.heritage.data.remote.request.GroupBasic
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.request.GroupSchedule
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import okhttp3.MultipartBody
import retrofit2.Response

class Repository constructor(context: Context) {

    // group
    suspend fun insertGroup(body: GroupAddRequest): Response<GroupListResponse> =
        groupApi.insertGroup(body)

    suspend fun selectAllGroups(): Response<List<GroupListResponse>> = groupApi.selectAllGroups()
    suspend fun deleteGroup(groupSeq: Int): Response<Boolean> = groupApi.deleteGroup(groupSeq)
    suspend fun changeGroupActiveState(body: Int): Response<Boolean> =
        groupApi.changeGroupActiveState(body)

    suspend fun selectGroupMembers(groupSeq: Int): Response<List<Member>> =
        groupApi.selectGroupMembers(groupSeq)
    suspend fun selectGroupDetail(groupSeq: Int): Response<GroupListResponse> =
        groupApi.selectGroupDetail(groupSeq)

    // group - 가입/신청/승인/취소/탈퇴
    suspend fun approveGroupJoin(groupSeq: Int, body: GroupBasic): Response<Boolean> =
        groupApi.approveGroupJoin(groupSeq, body)
    suspend fun applyGroupJoin(groupSeq: Int, body: GroupJoin): Response<Boolean> =
        groupApi.applyGroupJoin(groupSeq, body)
    suspend fun leaveGroupJoin(groupSeq: Int, userSeq: Int): Response<Boolean> =
        groupApi.leaveGroupJoin(groupSeq, userSeq)

    // group - 내 그룹 조회
    suspend fun selectMyGroups(): Response<List<MyGroupResponse>> =
        groupApi.selectMyGroups()

    // group -  목적지
    suspend fun insertGroupDestination(groupSeq: Int, heritageSeq: Int): Response<String> =
        groupApi.insertGroupDestination(groupSeq, heritageSeq)

    // group - 일정
    suspend fun insertGroupSchedule(groupSeq: Int, body: GroupSchedule): Response<String> =
        groupApi.insertGroupSchedule(groupSeq, body)
    suspend fun deleteGroupSchedule(groupSeq: Int, date: String): Response<String> =
        groupApi.deleteGroupSchedule(groupSeq, date)
    suspend fun selectGroupSchedule(groupSeq: Int): Response<List<GroupSchedule>> =
        groupApi.selectGroupSchedule(groupSeq)


    // user
    suspend fun checkEmail(userId: String): Response<String> = userApi.checkEmail(userId)
    suspend fun checkNickname(userNickname: String): Response<String> =
        userApi.checkNickname(userNickname)

    suspend fun signup(user: User): Response<String> = userApi.signup(user)
    suspend fun login(map: HashMap<String, String>): Response<String> = userApi.login(map)
    suspend fun emailAuth(userId: String): Response<String> = userApi.emailAuth(userId)

    suspend fun socialCheckId(userId: String): Response<String> = userApi.socialCheckId(userId)
    suspend fun socialLogin(map: HashMap<String, String>): Response<String> =
        userApi.socialLogin(map)

    suspend fun socialSignup(user: User): Response<String> = userApi.socialSignup(user)

    suspend fun resign(userId: String): Response<String> = userApi.resign(userId)

    suspend fun modifyProfile(userModify: UserModify): Response<String> =
        userApi.modifyProfile(userModify)

    suspend fun checkPassword(map: HashMap<String, String>): Response<String> =
        userApi.checkPassword(map)

    suspend fun insertHeritageScrap(scrap: HeritageScrap): Response<String> =
        userApi.insertHeritageScrap(scrap)

    suspend fun selectAllScraps(userSeq: Int): Response<List<Heritage>> =
        userApi.selectAllScraps(userSeq)

    suspend fun deleteHeritageScrap(userSeq: Int, heritageSeq: Int): Response<String> =
        userApi.deleteHeritageScrap(userSeq, heritageSeq)

    suspend fun selectAllMyKeyword(userSeq: Int): Response<List<Keyword>> =
        userApi.selectAllMyKeyword(userSeq)

    suspend fun insertMyKeyword(body: Keyword): Response<String> = userApi.insertMyKeyword(body)
    suspend fun deleteMyKeyword(myKeywordSeq: Int): Response<String> =
        userApi.deleteMyKeyword(myKeywordSeq)

    suspend fun selectAllMyDestination(userSeq: Int): Response<List<GroupDestinationMap>> =
        userApi.selectAllMyDestination(userSeq)

    suspend fun selectAllMySchedule(userSeq: Int): Response<List<Schedule>> = userApi.selectAllMySchedule(userSeq)
    suspend fun insertMySchedule(schedule: Schedule): Response<String> = userApi.insertMySchedule(schedule)
    suspend fun deleteMySchedule(scheduleSeq: Int): Response<String> = userApi.deleteMySchedule(scheduleSeq)

    suspend fun getMyNotiSetting(userSeq: Int): Response<Char> = userApi.getMyNotiSetting(userSeq)
    suspend fun setMyNotiSetting(userSeq: Int, userSetting: Char): Response<String> = userApi.setMyNotiSetting(userSeq, userSetting)
    suspend fun selectAllMyNoti(userSeq: Int): Response<String> = userApi.selectAllMyNoti(userSeq)

    suspend fun pushToken(token: FCMToken): Response<String> = userApi.pushToken(token)

    // heritage
    suspend fun selectAllHeritage(): Response<List<Heritage>> = heritageApi.selectAllHeritage()
    suspend fun insertHeritageReview(body: HeritageReviewListResponse): Response<String> =
        heritageApi.insertHeritageReview(body)
    suspend fun selectAllHeritageReviews(heritageSeq: Int): Response<List<HeritageReviewListResponse>> =
        heritageApi.selectAllHeritageReviews(heritageSeq)
    suspend fun deleteHeritageReview(heritageReviewSeq: Int, heritageSeq: Int): Response<String> =
        heritageApi.deleteHeritageReivew(heritageReviewSeq, heritageSeq)

    // feed
    suspend fun selectMyFeeds(): Response<List<FeedListResponse>> =
        feedApi.selectMyFeeds()
    suspend fun selectFeedsByHashtag(fhTag: String): Response<List<FeedListResponse>> =
        feedApi.selectFeedsByHashtag(fhTag)
    suspend fun selectAllFeeds(): Response<List<Feed>> =
        feedApi.selectAllFeeds()
    suspend fun insertFeed(body: FeedAddRequest): Response<FeedListResponse> =
        feedApi.insertFeed(body)

    // file
    suspend fun sendImage(url: String, file: MultipartBody.Part): Response<String> = fileApi.saveImage(url, file)


    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}