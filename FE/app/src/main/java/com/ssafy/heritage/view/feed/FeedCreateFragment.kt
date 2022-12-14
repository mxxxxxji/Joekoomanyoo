package com.ssafy.heritage.view.feed

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.databinding.FragmentFeedCreateBinding
import com.ssafy.heritage.util.FileUtil
import com.ssafy.heritage.util.FormDataUtil
import com.ssafy.heritage.viewmodel.FeedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "FeedCreateFragment___"
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class FeedCreateFragment : BaseFragment<FragmentFeedCreateBinding>(R.layout.fragment_feed_create) {

    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private val feedViewModel by activityViewModels<FeedViewModel>()
    private lateinit var feedInfo: FeedAddRequest

    var img_multipart: MultipartBody.Part? = null
    private var title: String = ""
    private var hashTags: String = ""
    private var content: String = ""
    private var feedOpen: Char = 'Y'

    override fun init() {
        binding.layoutImgStorke.isSelected = true
        initObserver()
        initClickListener()
//        setToolbar()
    }

    private fun initObserver() = with(binding) {
        feedViewModel.insertFeedInfo.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver: $it")
            if (it!! != "0") {
                Glide.with(ivFeedImage.context)
                    .load(it)
                    .into(ivFeedImage)
            }

//            if (it != null) {
//                makeToast("????????? ?????????????????????.")
////                val action = FeedCreateFragmentDirections.actionFeedCreateFragmentToFeedDetailFragment()
////                findNavController().navigate(action)
//            } else {
//                makeToast("?????? ????????? ??????????????????. ?????? ??????????????????.")
//            }
        }
    }

    private fun initClickListener() = with(binding) {
        // ?????? ??????
        ivFeedImage.setOnClickListener {
            // ?????? ??????
            if (!hasPermissions()) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                pick()
            }
        }

//        // ??????/????????? ??????
//        switchFeedCreateLock.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // ???????????? ??????
//                makeToast("??????")
//            } else {
//                // ?????????
//                feedOpen = 'N'
//                makeToast("?????????")
//            }
//        }

        // ?????? ???????????? ?????? ???
        tvGroupInsert.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                title = etFeedCreateTitle.editText?.text.toString()
                // ??????????????? ???????????? ?????? ???????????????
                var tags = getHashTags(binding.etFeedCreateTag.editText?.text.toString())
                var tagResult = listOf<String>()

                tags.forEach { tag ->
                    tagResult = tagResult.plus("${tag.value}")
                }

                // ????????? ??? ??????????????? "#"??? ??????
                // ?????? ???????????? map
                tagResult = tagResult.map {
                    it.replace("#", "")
                }

                Log.d(TAG, "?????????: $tagResult")
                content = etFeedCreateContent.editText?.text.toString()

//                when {
//                    title == "" -> {
//                        makeToast("????????? ??????????????????")
//                    }
//                    tagResult == [] -> {
//                        makeToast("??????????????? ??????????????????")
//                    }
//                }

                // imageUrl??? ??????????????? ??????..?????? ????????????!!
                if (img_multipart == null || img_multipart?.let { feedViewModel.sendImage(it) } == true) {
                    if (feedViewModel.insertFeedInfo.value == null) {
                        makeToast("?????? if???: ????????? ??????????????????")
                        binding.layoutImgStorke.isSelected = false
                    } else {
                        binding.layoutImgStorke.isSelected = true
                        feedInfo = FeedAddRequest(
                            userSeq,
                            feedViewModel.insertFeedInfo.value!!,
                            title,
                            content,
                            feedOpen,
                            tagResult
                        )
                        feedViewModel.insertFeed(feedInfo)
                        val action =
                            FeedCreateFragmentDirections.actionFeedCreateFragmentToFeedListFragment()
                        findNavController().navigate(action)
                        Log.d(TAG, "initClickListener: ${feedInfo}")
                        makeToast("????????? ?????????????????????")
                    }
                } else {
                    // ????????? ??????? ????????? ???????????? ?????? ????????? ?????? ??????????
                    return@launch
                }
            }
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // ?????? ??????
    private fun pick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActivityLauncher.launch(intent)
    }

    // ?????? ????????? ????????? ??????
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {

                binding.tvEx.visibility = View.GONE

                val imagePath = it.data!!.data
                binding.ivFeedImage.setImageURI(imagePath)
                val file = FileUtil.toFile(requireContext(), imagePath!!)
                img_multipart = FormDataUtil.getImageBody("file", file)

            } else if (it.resultCode == RESULT_CANCELED) {
                makeToast("?????? ?????? ??????")
            } else {
                Log.d("ActivityResult", "something wrong")
            }
        }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            pick()
        } else {
            // PERMISSION NOT GRANTED
            makeToast("????????? ?????????")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.feed_create_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.feed_create_menu, menu)
    }

//    private fun setToolbar() = with(binding) {
//        toolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.action_write_feed -> {
//                } else -> {
//                    tvGroupInsert.setOnClickListener()
//                    false
//                }
//            }
//        }
//    }


    private fun getHashTags(text: String): Sequence<MatchResult> {
        val pattern = """#([^#\s]+)""" // ?????? ?????? ?????????

        val regex = pattern.toRegex()
        val matches = regex.findAll(text)

        return matches
    }

    fun hasPermissions() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

}