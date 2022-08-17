package com.ssafy.heritage.view.profile

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.KeywordListAdapter
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileBinding
import com.ssafy.heritage.listener.KeywordListLongClickListener
import com.ssafy.heritage.listener.SettingListClickListener
import com.ssafy.heritage.util.ProfileSetting.LOGOUT
import com.ssafy.heritage.util.ProfileSetting.MODIFY_PROFILE
import com.ssafy.heritage.util.ProfileSetting.MY_TRIP
import com.ssafy.heritage.util.ProfileSetting.SCHEDULE
import com.ssafy.heritage.util.ProfileSetting.SCRAP
import com.ssafy.heritage.util.ProfileSetting.SIGNOUT
import com.ssafy.heritage.view.dialog.KeywordDialog
import com.ssafy.heritage.view.dialog.YesOrNoDialog
import com.ssafy.heritage.view.login.LoginActivity
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProfileFragment___"

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }
    private val keywordListAdapter: KeywordListAdapter by lazy { KeywordListAdapter() }
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val keywordDialog: KeywordDialog by lazy { KeywordDialog() }

    override fun init() {

        binding.user = userViewModel.user?.value!!

        initAdapter()

        initObserver()

        initClickListener()

        setToolbar()
    }


    override fun onStart() {
        super.onStart()
        userViewModel.getKeywordList(userViewModel.user.value?.userSeq!!)
    }

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter

            addItemDecoration(
                com.ssafy.heritage.util.DividerItemDecoration(
                    5F,
                    resources.getColor(R.color.athens_gray)
                )
            )

            settingListAdapter.settingListClickListener = object : SettingListClickListener {
                override fun onClick(position: Int, view: View) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
                        MY_TRIP -> {
                            findNavController().navigate(R.id.action_profileFragment_to_myDataFragment)
                        }
                        SCHEDULE -> {
                            findNavController().navigate(R.id.action_profileFragment_to_myCalendarFragment)
                        }
                        SCRAP -> {
                            findNavController().navigate(R.id.action_profileFragment_to_scrapListFragment)
                        }
                        MODIFY_PROFILE -> {
                            findNavController().navigate(R.id.action_profileFragment_to_passwordRequestFragment)
                        }
                        LOGOUT -> {
                            // 유저가 소셜로그인인 경우 구글 로그아웃도 해주셈
                            ApplicationClass.sharedPreferencesUtil.deleteToken()
                            Intent(requireContext(), LoginActivity::class.java).apply {
                                startActivity(this)
                                requireActivity().finish()
                            }
                        }
                        SIGNOUT -> {
                            Withdrawal()
                        }
                    }
                }
            }

            settingListAdapter.submitList(settingList)
        }

        // 키워드 리스트
        recyclerviewKeyword.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = keywordListAdapter

            keywordListAdapter.kywordListLongClickListener = object : KeywordListLongClickListener {
                override fun onClick(position: Int, scrapSeq: Int) {
                    val yesOrNoDialog =
                        YesOrNoDialog(title = "태그를 삭제할까요",
                            positive = "삭제",
                            negative = "취소",
                            positiveClick = { userViewModel.deleteKeyword(scrapSeq) },
                            negativeClick = { }
                        )

                    yesOrNoDialog.show(childFragmentManager, "yesOrNoDialog")
                }
            }
        }

    }

    private fun initObserver() {

        userViewModel.user.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver asd: $it")
            binding.user = it
        }
        userViewModel.keywordList.observe(viewLifecycleOwner) {
            keywordListAdapter.submitList(it)
        }
    }

    private fun initClickListener() = with(binding) {

        // 키워드 추가 클릭시
        btnAddKeyword.setOnClickListener {
            keywordDialog.show(childFragmentManager, "keywordDialog")
        }
    }

    private fun setToolbar() = with(binding) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                // 설정 메뉴 클릭시
                R.id.setting -> {
                    findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    val settingList = arrayListOf<String>(MY_TRIP, SCHEDULE, SCRAP, MODIFY_PROFILE, LOGOUT, SIGNOUT)


    // 소셜 로그아웃
    private fun signOutGoogle() {

        mGoogleSignInClient.signOut()
            // 로그아웃 성공시
            .addOnSuccessListener {
                Log.d(TAG, "signOutGoogle: ")
            }
    }

    private fun Withdrawal() {

        CoroutineScope(Dispatchers.Main).launch {
            if (userViewModel.withdrawal() == true) {
                makeToast("회원 탈퇴가 완료되었습니다")
                ApplicationClass.sharedPreferencesUtil.deleteToken()
                Intent(requireContext(), LoginActivity::class.java).apply {
                    startActivity(this)
                }
                requireActivity().finish()
            } else {
                makeToast("탈퇴에 실패하였습니다")
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}