package com.ganeshi.savestatus.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ganeshi.savestatus.data.StatusRepo
import com.ganeshi.savestatus.databinding.FragmentFragmenetSaveStatusBinding
import com.ganeshi.savestatus.utils.Constants
import com.ganeshi.savestatus.utils.SharedPrefUtils

import com.ganeshi.savestatus.utils.getFolderPermissions
import com.ganeshi.savestatus.viewmodels.StatusViewModel
import com.ganeshi.savestatus.viewmodels.factories.StatusViewModelFactory
import com.ganeshi.savestatus.views.adapters.MediaViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class FragmentSaveStatus : Fragment() {
    private val binding by lazy {
        FragmentFragmenetSaveStatusBinding.inflate(layoutInflater)
    }
    private lateinit var type: String
    private val WHATSAPP_REQUEST_CODE = 102
    private val WHATSAPP_BUSINEES_REQUEST_CODE = 101
    private val viewPagerTitle = arrayListOf("Images" , "Videos")
    lateinit var  viewModel: StatusViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {
                val repo = StatusRepo(requireActivity())
                viewModel = ViewModelProvider(requireActivity() , StatusViewModelFactory(repo))[StatusViewModel::class.java]
                type = it.getString(
                    Constants.FRAGMENT_TYPE_KEY, ""
                )
                textViews.text = type
                when (type) {
                    Constants.TYPE_WHATSAPP_BUSINESS -> {
                            // permission check
                            // granted then fetch status
                            //get permission
                            // fetch status
                            val isPermissionGranted = SharedPrefUtils.getPrefBoolean(SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED , false)
                            if (isPermissionGranted){
                                getWhatsAppBusinessStatus()
                            }

                        permissionLayout.butPermission.setOnClickListener {
                            getFolderPermissions(
                                context = requireActivity(),
                                REQUEST_CODE = WHATSAPP_BUSINEES_REQUEST_CODE,
                                initialUri = Constants.getWhatsappBusinessUri()
                            )
                        }
                        val viewPagerAdapter = MediaViewPagerAdapter(requireActivity() , imageType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES , videoType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS)
                        statusViewPager.adapter = viewPagerAdapter
                        TabLayoutMediator(tabLayout, statusViewPager){tab , pos->
                            tab.text = viewPagerTitle[pos]
                        }.attach()
                    }

//                    Constants.TYPE_WHATSAPP_MAIN -> {
//                            // permission check
//                            // granted then fetch status
//                            //get permission
//                            // fetch status
//
//                        permissionLayout.butPermission.setOnClickListener {
//                            getFolderPermissions(
//                                context = requireActivity(),
//                                REQUEST_CODE = WHATSAPP_REQUEST_CODE,
//                                initialUri = Constants.getWhatsappUri()
//                            )
//                        }
//                    }
                }
            }
        }
    }


    fun getWhatsAppBusinessStatus() {
        binding.permissionLayoutHolder.visibility = View.GONE
        viewModel.getWhatsAppBusinessStatus()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            val treeUri = data?.data
            if (requestCode == WHATSAPP_BUSINEES_REQUEST_CODE && treeUri != null) {
                // Whatsapp business logic here
                requireActivity().contentResolver.takePersistableUriPermission(
                    treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                SharedPrefUtils.putPrefString(
                    SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI,
                    treeUri.toString()
                )
                SharedPrefUtils.putPrefBoolean(
                    SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED,
                    true
                )
                getWhatsAppBusinessStatus()
            }
        }
    }
}


