package com.ganeshi.savestatus.views.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.provider.SyncStateContract
import android.provider.SyncStateContract.Constants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.databinding.FragmentFragmenetSaveStatusBinding
import com.ganeshi.savestatus.utils.Constant
import com.ganeshi.savestatus.utils.getFolderPermissions


class FragmentSaveStatus : Fragment() {
    private val binding by lazy {
        FragmentFragmenetSaveStatusBinding.inflate(layoutInflater)
    }
    private  lateinit var type:String
    private val WHATSAPP_REQUEST_CODE = 101
    private val WHATSAPP_BUSINEES_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {
                type = it.getString(Constant.FRAGMENT_TYPE_KEY , "")
                textViews.text = type

                when(type){
                    Constant.TYPE_WHATSAPP_MAIN->{

                        // check permission
                        // granted then fetch status
                        // get permission
                        // fetch status

                        permissionLayout.butPermission.setOnClickListener{

                            getFolderPermissions(
                                context = requireActivity(),
                                REQUEST_CODE = WHATSAPP_REQUEST_CODE,
                                initialUri = Constant.getWhatsappUri()
                            )
                        }



                    }
                    Constant.TYPE_WHATSAPP_BUSINESS->{
                        // check permission
                        // granted then fetch permission
                        // get permission
                        // fetch status
                        permissionLayout.butPermission.setOnClickListener{
                            getFolderPermissions(
                                context = requireContext(),
                                REQUEST_CODE = WHATSAPP_BUSINEES_REQUEST_CODE,
                                initialUri = Constant.getWhatsappBusinessUri()
                            )
                        }




                    }
                }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}



