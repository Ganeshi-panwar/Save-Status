package com.ganeshi.savestatus.views.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ganeshi.savestatus.databinding.FragmentFragmenetSaveStatusBinding
import com.ganeshi.savestatus.utils.Constant
import com.ganeshi.savestatus.utils.getFolderPermissions


class FragmentSaveStatus : Fragment() {
    private val binding by lazy {
        FragmentFragmenetSaveStatusBinding.inflate(layoutInflater)
    }
    private lateinit var type: String
    private val WHATSAPP_REQUEST_CODE = 102
    private val WHATSAPP_BUSINEES_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {
                type = it.getString(Constant.FRAGMENT_TYPE_KEY, "")
                textViews.text = type
                when(type){
                    Constant.TYPE_WHATSAPP_BUSINESS ->{
                        permissionLayout.butPermission.setOnClickListener {
                            // permission check
                            // granted then fetch status
                            //get permission
                            // fetch status

                            getFolderPermissions(
                                context = requireActivity(),
                                REQUEST_CODE = WHATSAPP_BUSINEES_REQUEST_CODE,
                                initialUri = Constant.getWhatsappBusinessUri()
                            )

                        }

                    }

                    Constant.TYPE_WHATSAPP_MAIN -> {
                            // permission check
                            // granted then fetch status
                            //get permission
                            // fetch status

                        permissionLayout.butPermission.setOnClickListener {
                            getFolderPermissions(
                                context = requireActivity(),
                                REQUEST_CODE = WHATSAPP_REQUEST_CODE,
                                initialUri = Constant.getWhatsappUri()
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
        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
               WHATSAPP_BUSINEES_REQUEST_CODE -> {
                    openWhatsAppWithUri(Constant.getWhatsappBusinessUri())
                }
                WHATSAPP_REQUEST_CODE -> {
                    openWhatsAppWithUri(Constant.getWhatsappUri())
                }
            }
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openWhatsAppWithUri(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri.toString())
        startActivity(intent)
    }
}

