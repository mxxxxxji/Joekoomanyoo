package com.ssafy.heritage.data.repository

import android.content.Context
import com.ssafy.heritage.data.dto.GroupAttribute
import com.ssafy.heritage.data.remote.api.RetrofitInstance.groupApi
import com.ssafy.heritage.data.remote.response.GroupListResponse
import retrofit2.Response
import retrofit2.http.*

class Repository constructor(context: Context) {

    // group
    suspend fun insertGroup(body: GroupListResponse): Response<Boolean> = groupApi.insertGroup(body)
    suspend fun selectAllGroups(): Response<List<GroupListResponse>> = groupApi.selectAllGroups()
    suspend fun deleteGroup(groupSeq: Int): Response<Boolean> = groupApi.deleteGroup(groupSeq)
    suspend fun changeGroupActiveState(body: Int): Response<Boolean> = groupApi.changeGroupActiveState(body)


    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }
        fun get(): Repository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
}