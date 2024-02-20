package com.ganeshi.savestatus.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.databinding.FragmentMediaBinding
import com.ganeshi.savestatus.utils.Constants

class FragmentMedia : Fragment() {
    private val binding by lazy {
        FragmentMediaBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let{
                val mediaType = it.getString(Constants.MEDIA_TYPE_KEY , "")
                tempMediaText.text = mediaType

            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
    }


