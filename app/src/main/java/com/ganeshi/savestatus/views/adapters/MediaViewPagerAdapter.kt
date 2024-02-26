package com.ganeshi.savestatus.views.adapters

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ganeshi.savestatus.views.fragments.FragmentMedia

class MediaViewPagerAdapter(private val fragmentActivity: FragmentActivity,
    private val imageType:String = com.ganeshi.savestatus.utils.Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES,
    private val videoType:String = com.ganeshi.savestatus.utils.Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS):
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {

        return when(position){
            0->{
                //image media fragment
                val mediaFragment = FragmentMedia()
                val bundle = Bundle()
                bundle.putString(com.ganeshi.savestatus.utils.Constants.MEDIA_TYPE_KEY , imageType)
                mediaFragment.arguments = bundle
                mediaFragment
            }else->{
                // video media fragment
                val mediaFragment = FragmentMedia()
                val bundle = Bundle()
                bundle.putString(com.ganeshi.savestatus.utils.Constants.MEDIA_TYPE_KEY , videoType)
                mediaFragment.arguments = bundle
                mediaFragment
            }
    }
    }

}