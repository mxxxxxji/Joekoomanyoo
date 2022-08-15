package com.ssafy.heritage.view.group


import android.view.View
import androidx.fragment.app.viewModels
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.EvaluationMemberListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentEvaluationListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "EvaluationListFragment___"

class EvaluationListFragment :
    BaseFragment<FragmentEvaluationListBinding>(R.layout.fragment_evaluation_list),
    OnItemClickListener {

    private lateinit var evaluationMemberListAdapter: EvaluationMemberListAdapter
    private val groupViewModel by viewModels<GroupViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    val slidePanel = binding.mainFrame

    override fun init() {
           // SlidingUpPanel
        slidePanel.addPanelSlideListener(PanelEventListener())  // 이벤트 리스너 추가
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        evaluationMemberListAdapter = EvaluationMemberListAdapter(this)
        binding.recyclerviewEvaluationMemberList.adapter = evaluationMemberListAdapter
    }
    private fun initObserver() {}
    private fun initClickListener() {}
    override fun onItemClick(position: Int) {
        val state = slidePanel.panelState
        // 닫힌 상태일 경우 열기
        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }
        // 열린 상태일 경우 닫기
        else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }
    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            binding.tv.text = slideOffset.toString()
        }

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
            if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                //binding.btnToggle.text = "열기"
            } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                //binding.btnToggle.text = "닫기"
            }
        }
    }
}