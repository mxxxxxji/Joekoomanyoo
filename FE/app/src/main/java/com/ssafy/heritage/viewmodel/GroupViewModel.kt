package com.ssafy.heritage.viewmodel

import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.S
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.GroupAddRequest
import com.ssafy.heritage.data.remote.request.GroupBasic
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.request.GroupSchedule
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

private const val TAG = "GroupViewModel___"

class GroupViewModel : ViewModel() {

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val _groupList = SingleLiveEvent<MutableList<GroupListResponse>>()
    val groupList: LiveData<MutableList<GroupListResponse>> get() = _groupList

    private val _groupMemberList = SingleLiveEvent<MutableList<Member>>()
    val groupMemberList: LiveData<MutableList<Member>> get() = _groupMemberList

    private val _groupPermission = SingleLiveEvent<Int>()
    val groupPermission: LiveData<Int> get() = _groupPermission

    private val _insertGroupInfo = SingleLiveEvent<GroupListResponse>()
    val insertGroupInfo: LiveData<GroupListResponse> get() = _insertGroupInfo

    private val _detailInfo = MutableLiveData<GroupListResponse>()
    val detailInfo: LiveData<GroupListResponse> get() = _detailInfo

    private val _approveState = SingleLiveEvent<Boolean>()
    val approveState: LiveData<Boolean> get() = _approveState

    private val _applyState = SingleLiveEvent<Boolean>()
    val applyState: LiveData<Boolean> get() = _applyState

    private val _myGroupList = SingleLiveEvent<MutableList<MyGroupResponse>>()
    val myGroupList: LiveData<MutableList<MyGroupResponse>> get() = _myGroupList

    private val _insertGroupDestination = SingleLiveEvent<String>()
    val insertGroupDestination: LiveData<String> get() = _insertGroupDestination

    private val _selectGroupScheduleList = SingleLiveEvent<MutableList<GroupSchedule>>()
    val selectGroupScheduleList: LiveData<MutableList<GroupSchedule>> get() = _selectGroupScheduleList

    fun add(info: GroupListResponse) {
        _detailInfo.postValue(info)
    }


    fun setGroupPermission(groupPermission: Int) {
        _groupPermission.postValue(groupPermission)
    }

    fun getGroupList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectAllGroups().let { response ->
                if (response.isSuccessful) {
                    var list = response.body()!! as MutableList<GroupListResponse>
                    // list.sortBy { it.groupCreatedAt } // 최신순 정렬
                    _groupList.postValue(list)
                } else {
                    Log.d(TAG, "getGroupList : ${response.code()}")
                }

            }
        }
    }

    suspend fun selectGroupMembers(userSeq: Int, groupSeq: Int) = withContext(Dispatchers.Main) {
        repository.selectGroupMembers(groupSeq).let { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "현재 유저 :${userSeq}")
                var list = response.body()!! as MutableList<Member>
                Log.d(TAG, "selectGroupMembers : ${list}")
                Log.d(TAG, "현재 유저 :${userSeq}")

                _groupPermission.value = list.find { it.userSeq == userSeq }?.memberStatus ?: 3

//                    for(i in list){
//                        if(i.userSeq == userSeq){
//                            Log.d(TAG,"현재 유저 :${userSeq}, PERMISSION: ${i.memberStatus} ")
//                            _groupPermission.postValue(i.memberStatus)
//                        }else{ // 그 외의 경우
//                            Log.d(TAG,"현재 유저 :${userSeq}")
//                            _groupPermission.postValue(3)
//                        }
//                    }

//                    Log.d(TAG,"현재 유저 :${userSeq}, PERMISSION_groupPermission: ${_groupPermission.value}")
//                    Log.d(TAG,"현재 유저 :${userSeq}, PERMISSION groupPermission: ${groupPermission.value}")
//                    _groupMemberList.postValue(list)
                true
            } else {
                Log.d(TAG, "selectGroupMembers : ${response.code()}")
                null
            }
        }
    }

    fun insertGroup(groupInfo: GroupAddRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGroup(groupInfo).let { response ->
                if (response.isSuccessful) {
                    var info = response.body()!! as GroupListResponse
                    _insertGroupInfo.postValue(info)
                } else {
                    Log.d(TAG, "insertGroup: ${response.code()}")
                }
            }
        }
    }

    // 가입승인
    fun approveGroupJoin(groupSeq: Int, groupBasic: GroupBasic) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.approveGroupJoin(groupSeq, groupBasic).let { response ->
                if (response.isSuccessful) {
                    val result = response.body()!! as Boolean
                    Log.d(TAG, "approveGroupJoin: $result")
                    _approveState.postValue(result)
                } else {
                    Log.d(TAG, "approveGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입요청
    fun applyGroupJoin(groupSeq: Int, groupJoin: GroupJoin) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.applyGroupJoin(groupSeq, groupJoin).let { response ->
                if (response.isSuccessful) {
                    //val result = response.body()!! as Boolean
                    //Log.d(TAG, "applyGroupJoin: $result")
                    _applyState.postValue(true)
                    _groupPermission.postValue(0)
                } else {
                    Log.d(TAG, "applyGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입 취소, 거절, 탈퇴
    fun leaveGroupJoin(groupSeq: Int, userSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.leaveGroupJoin(groupSeq, userSeq).let { response ->
                if (response.isSuccessful) {
                    _groupPermission.postValue(3)
                } else {
                    Log.d(TAG, "leaveGroupJoin: ${response.code()}")
                }
            }
        }
    }

    fun selectMyGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectMyGroups().let { response ->
                if (response.isSuccessful) {
                    var info = response.body()!! as MutableList<MyGroupResponse>
                    Log.d(TAG, "selectMyGroups: ${response}")
                    _myGroupList.postValue(info)
                } else {
                    Log.d(TAG, "selectMyGroups: ${response.code()}")
                }
            }
        }
    }

    fun insertGroupDestination(
        groupSeq: Int,
        heritageSeq: Int,
        groupName: String,
        heritageName: String
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.insertGroupDestination(groupSeq, heritageSeq).let { response ->
                Log.d(TAG, "insertGroupDestination response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "insertGroupDestination isSuccessful: ${response.body()}")
                    if (response.body() == "Success") {
                        makeToast("${groupName}에 ${heritageName}이(가) 추가되었습니다.")
                    } else {
                        makeToast("이미 추가된 모임입니다.")
                    }
                } else {
                    Log.d(TAG, "insertGroupDestination: ${response.code()}")
                }
            }
        }
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }

    // 그룹 일정 등록
    fun insertGroupSchedule(groupSeq: Int, body: GroupSchedule){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGroupSchedule(groupSeq, body).let { response ->
                if (response.isSuccessful) {
                    if(response.body() == "Success"){
                        Log.d(TAG, "insertGroupSchedule - ${response.body()}: 일정이 등록되었습니다")
                    }else{
                        Log.d(TAG, "insertGroupSchedule - ${response.body()}: 이미 등록된 일정입니다")
                    }
                } else {
                    Log.d(TAG, "insertGroupSchedule: ${response.code()}")
                }
            }
        }
    }

    // 그룹 일정 삭제
    fun deleteGroupSchedule(groupSeq: Int, date: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGroupSchedule(groupSeq, date).let { response ->
                if (response.isSuccessful) {
                    if(response.body() == "Success"){
                        Log.d(TAG, "deleteGroupSchedule - ${response.body()}: 일정이 삭제되었습니다")
                    }else{
                        Log.d(TAG, "deleteGroupSchedule - ${response.body()}: 이미 삭제된 일정입니다")
                    }
                } else {
                    Log.d(TAG, "deleteGroupSchedule: ${response.code()}")
                }
            }
        }
    }

    // 그룹 일정 조회
    fun selectGroupSchedule(groupSeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupSchedule(groupSeq).let { response ->
                if (response.isSuccessful) {
                    var list = response.body()!! as MutableList<GroupSchedule>
                    _selectGroupScheduleList.postValue(list)
                } else {
                    Log.d(TAG, "selectGroupSchedule: ${response.code()}")
                }
            }
        }
    }
}