package com.ssafy.heritage.view.group

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.ChatListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Chat
import com.ssafy.heritage.databinding.FragmentGroupChatBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.OkHttpClient
import okhttp3.Request


private const val TAG = "GroupChatFragment___"

class GroupChatFragment : BaseFragment<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {

    private val chatListAdapter: ChatListAdapter by lazy { ChatListAdapter(userViewModel.user.value?.userSeq!!) }

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    lateinit var stomp: StompClient

    override fun init() {

        initView()

        initAdapter()

        initObserer()

        initClickListener()

        setTextChangedListener()

        initStomp()
    }

    private fun initAdapter() = with(binding) {
        recyclerView.apply {
            adapter = chatListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initObserer() {
        groupViewModel.chatList.observe(viewLifecycleOwner) {
            chatListAdapter.submitList(it)
        }
    }

    @SuppressLint("CheckResult")
    private fun initStomp() {

        // 1. INIT
        val url = "wss://i7d102.p.ssafy.io/stomp/chat"
        val intervalMillis = 1000L
        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("X-AUTH-TOKEN", ApplicationClass.sharedPreferencesUtil.getToken()!!)
            .url(url)
            .build()
        stomp = StompClient(client, intervalMillis)
        stomp.url = url

        // 2. CONNECT
        val stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    Log.d(TAG, "initStomp: OPENED")
                }
                Event.Type.CLOSED -> {
                    Log.d(TAG, "initStomp: CLOSED")
                }
                Event.Type.ERROR -> {
                    Log.d(TAG, "initStomp: ERROR")
                }
                else -> {}
            }
        }

        // 3. SUBSCRIBE
        val groupSeq = groupViewModel.detailInfo.value?.groupSeq!!
        stomp.join("/sub/chat/room/${groupSeq}")
            .doOnError { error ->
                Log.d(TAG, "initStomp error: $error")
            }
            .subscribe { message ->
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d(TAG, "initStomp 받은 메시지: $message")
                    val chat = Json.decodeFromString<Chat>(message)
                    groupViewModel.addChat(chat)
                }
            }
    }

    private fun initView() = with(binding) {
        tilChat.requestFocus()
    }

    private fun initClickListener() = with(binding) {

    }

    private fun setTextChangedListener() = with(binding) {

        // id 이메일 입력창 에러 비활성화
        tilChat.editText?.addTextChangedListener {
            tilChat.isErrorEnabled = false
            if (it?.length ?: 0 < 1) {
                tilChat.endIconMode = END_ICON_NONE
            } else {
                tilChat.endIconMode = END_ICON_CUSTOM
                tilChat.setEndIconOnClickListener {

                    val groupSeq = groupViewModel.detailInfo.value?.groupSeq!!
                    val chatContent = tilChat.editText!!.text.toString()
                    val chatImgUrl = ""
                    val userSeq = userViewModel.user.value?.userSeq!!

                    val chat = Chat(
                        groupSeq = groupSeq,
                        userSeq = userSeq,
                        chatContent = chatContent,
                        chatImgUrl = chatImgUrl

                    )

                    val serializer = serializer(Chat::class.java)
                    val string = Json.encodeToString(serializer, chat)

                    Log.d(TAG, "Json: $string")

                    stomp.send("/pub/chat/message", string).subscribe {
                        if (it) {
                            Log.d(TAG, "send: $it")
                        }
                    }

                    tilChat.editText?.text?.clear()
                }
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}