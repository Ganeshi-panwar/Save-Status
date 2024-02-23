package com.ganeshi.savestatus.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ganeshi.savestatus.data.StatusRepo
import com.ganeshi.savestatus.databinding.FragmentMediaBinding
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.Constants
import com.ganeshi.savestatus.viewmodels.StatusViewModel
import com.ganeshi.savestatus.viewmodels.factories.StatusViewModelFactory
import com.ganeshi.savestatus.views.adapters.MediaAdapter

class FragmentMedia : Fragment() {
    private val binding by lazy {
        FragmentMediaBinding.inflate(layoutInflater)
    }
lateinit var viewModel: StatusViewModel
lateinit var adapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {

                val repo = StatusRepo(requireActivity())
                viewModel = ViewModelProvider(
                    requireActivity(),
                    StatusViewModelFactory(repo)
                )[StatusViewModel::class.java]
                val mediaType = it.getString(Constants.MEDIA_TYPE_KEY, "")
//                tempMediaText.text = mediaType
                when (mediaType) {
                    Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES -> {
                        viewModel.whatsAppBusinessImageData.observe(requireActivity()) { unFilteredList ->
                            val filterList = unFilteredList.distinctBy { model ->
                                model.fileName
                            }
                            val list = ArrayList<MediaModel>()
                            filterList.forEach { model ->
                                list.add(model)
                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter
                        }
                    }

                    Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS -> {
                        viewModel.whatsAppBusinessVideoData.observe(requireActivity()) { unFilteredList ->
                            val filterList = unFilteredList.distinctBy { model ->
                                model.fileName
                            }
                            val list = ArrayList<MediaModel>()
                            filterList.forEach { model ->
                                list.add(model)
                            }
                            adapter = MediaAdapter(list, requireActivity())
                            Log.d("afg" , adapter.toString())
                            mediaRecyclerView.adapter = adapter
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
    }


