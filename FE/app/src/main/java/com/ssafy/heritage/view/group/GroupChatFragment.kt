package com.ssafy.heritage.view.group

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.ChatListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Chat
import com.ssafy.heritage.databinding.FragmentGroupChatBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.OkHttpClient


private const val TAG = "GroupChatFragment___"

class GroupChatFragment : BaseFragment<FragmentGroupChatBinding>(R.layout.fragment_group_chat) {

    private val chatListAdapter: ChatListAdapter by lazy { ChatListAdapter(userViewModel.user.value?.userSeq!!) }

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val groupViewModelFrag by viewModels<GroupViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    lateinit var stomp: StompClient
    lateinit var stompConnection: Disposable

    override fun init() {
        groupViewModelFrag.add(groupViewModel.detailInfo.value!!)
        groupViewModelFrag.getChatList(groupViewModel.detailInfo.value?.groupSeq!!)

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
            addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
                // 키보드가 올라와 높이가 변함
                if (bottom < oldBottom) {
                    this.scrollBy(0, oldBottom - bottom) // 스크롤 유지를 위해 추가
                }
            }
        }
    }

    private fun initObserer() {
        groupViewModelFrag.chatList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserer chatList: $it")
            chatListAdapter.submitList(it)
            binding.recyclerView.scrollToPosition(it.size - 1)
        }
    }

    @SuppressLint("CheckResult")
    private fun initStomp() {

        // 1. INIT
        val url = "wss://i7d102.p.ssafy.io/stomp/chat"
        val intervalMillis = 1000L
        val client = OkHttpClient()
        stomp = StompClient(client, intervalMillis)
        stomp.url = url

        // 2. CONNECT
        stompConnection = stomp.connect().subscribe {
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
                    groupViewModelFrag.addChat(chat)
                    chatListAdapter.submitList(groupViewModelFrag.chatList.value)
                    chatListAdapter.notifyDataSetChanged()
                    binding.recyclerView.scrollToPosition(groupViewModelFrag.chatList.value!!.size - 1)
                }
            }
    }

    private fun initView() = with(binding) {
        tilChat.requestFocus()
    }

    private fun initClickListener() = with(binding) {

    }

    private fun setTextChangedListener() = with(binding) {

        // 입력창 에러 비활성화
        tilChat.editText?.addTextChangedListener {
            tilChat.isErrorEnabled = false

            if (it?.length ?: 0 < 1) {
                tilChat.endIconMode = END_ICON_NONE
            } else {
                tilChat.endIconMode = END_ICON_CUSTOM
                tilChat.setEndIconOnClickListener {

                    val groupSeq = groupViewModel.detailInfo.value?.groupSeq!!
                    val chatContent = tilChat.editText!!.text.toString()
                    val chatImgUrl = "0"
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
                    tilChat.requestFocus()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stompConnection.dispose()
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}