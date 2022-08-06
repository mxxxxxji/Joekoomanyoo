package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.GroupAttribute
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.remote.request.GroupBasic
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Body

private const val TAG = "GroupViewModel___"

class GroupViewModel: ViewModel() {

    private val repository = Repository.get()

    private val _groupList = SingleLiveEvent<MutableList<GroupListResponse>>()
    val groupList: LiveData<MutableList<GroupListResponse>> get() = _groupList

    private val _groupMemberList = SingleLiveEvent<MutableList<Member>>()
    val groupMemberList: LiveData<MutableList<Member>> get() = _groupMemberList

    private val _groupPermission = SingleLiveEvent<Int>()
    val groupPermission: LiveData<Int> get() = _groupPermission

    private val _insertGroupInfo = SingleLiveEvent<GroupListResponse>()
    val insertGroupInfo: LiveData<GroupListResponse> get() = _insertGroupInfo

    private val _detailInfo = MutableLiveData<GroupListResponse>()
    val detailInfo : LiveData<GroupListResponse> get() = _detailInfo

    private val _approveState = SingleLiveEvent<Boolean>()
    val approveState: LiveData<Boolean> get() = _approveState

    private val _applyState = SingleLiveEvent<Boolean>()
    val applyState: LiveData<Boolean> get() = _applyState


    fun add(info : GroupListResponse){
        _detailInfo.postValue(info)
    }


    fun setGroupPermission(groupPermission: Int){
        _groupPermission.postValue(groupPermission)
    }
    fun getGroupList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectAllGroups().let { response ->
                if(response.isSuccessful){
                    var list = response.body()!! as MutableList<GroupListResponse>
                   // list.sortBy { it.groupCreatedAt } // 최신순 정렬
                    _groupList.postValue(list)
                }else{
                    Log.d(TAG, "getGroupList : ${response.code()}")
                }

            }
        }
    }

    fun selectGroupMembers(userSeq:Int, groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupMembers(groupSeq).let { response ->
                if(response.isSuccessful) {
                    Log.d(TAG,"현재 유저 :${userSeq}")
                    var list = response.body()!! as MutableList<Member>
                    Log.d(TAG, "selectGroupMembers : ${list}")
                    Log.d(TAG,"현재 유저 :${userSeq}")
                    for(i in list){
                        if(i.userSeq == userSeq){
                            Log.d(TAG,"현재 유저 :${userSeq}, PERMISSION: ${i.memberStatus} ")
                            _groupPermission.postValue(i.memberStatus)
                        }else{ // 그 외의 경우
                            Log.d(TAG,"현재 유저 :${userSeq}")
                            _groupPermission.postValue(3)
                        }
                    }
                    Log.d(TAG,"현재 유저 permission:${_groupPermission.value}")
                    _groupMemberList.postValue(list)
                }else{
                    Log.d(TAG, "selectGroupMembers : ${response.code()}")
                }
            }
        }
    }

    fun insertGroup(groupInfo: GroupListResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGroup(groupInfo).let { response ->
                if(response.isSuccessful) {
                    var info = response.body()!! as GroupListResponse
                    _insertGroupInfo.postValue(info)
                }else{
                    Log.d(TAG, "insertGroup: ${response.code()}")
                }
            }
        }
    }

    // 가입승인
    fun approveGroupJoin(groupSeq: Int, groupBasic: GroupBasic) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.approveGroupJoin(groupSeq, groupBasic).let { response ->
                if(response.isSuccessful) {
                    val result = response.body()!! as Boolean
                    Log.d(TAG, "approveGroupJoin: $result")
                    _approveState.postValue(result)
                }else{
                    Log.d(TAG, "approveGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입요청
    fun applyGroupJoin(groupSeq: Int, groupJoin: GroupJoin) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.applyGroupJoin(groupSeq, groupJoin).let { response ->
                if(response.isSuccessful) {
                    //val result = response.body()!! as Boolean
                    //Log.d(TAG, "applyGroupJoin: $result")
                    _applyState.postValue(true)
                    _groupPermission.postValue(0)
                }else{
                    Log.d(TAG, "applyGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입 취소, 거절, 탈퇴
    fun leaveGroupJoin(groupSeq: Int, groupBasic: GroupBasic){
        viewModelScope.launch(Dispatchers.IO) {
            repository.leaveGroupJoin(groupSeq, groupBasic).let { response ->
                if(response.isSuccessful) {
                    _groupPermission.postValue(3)
                }else{
                    Log.d(TAG, "leaveGroupJoin: ${response.code()}")
                }
            }
        }
    }
}