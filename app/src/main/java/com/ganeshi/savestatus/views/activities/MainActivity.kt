package com.ganeshi.savestatus.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.databinding.ActivityMainBinding
import com.ganeshi.savestatus.utils.Constant
import com.ganeshi.savestatus.utils.SharedPrefUtils
import com.ganeshi.savestatus.utils.replaceFragment
import com.ganeshi.savestatus.views.fragments.FragmentMessageRecovery
import com.ganeshi.savestatus.views.fragments.FragmentSaveStatus
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private val activity = this
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_SaveStatus)
        setContentView(binding.root)
        SharedPrefUtils.init(activity)
        binding.apply {
            val fragmentWhatsappStatus = FragmentSaveStatus()
            val bundle = Bundle()
            bundle.putString(Constant.FRAGMENT_TYPE_KEY , Constant.TYPE_WHATSAPP_MAIN)
            replaceFragment(fragmentWhatsappStatus , bundle)
            bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.saveStatus ->{
                        val fragmentWhatsAppStatus = FragmentSaveStatus()
                        val bundle = Bundle()
                            bundle.putString(Constant.FRAGMENT_TYPE_KEY , Constant.TYPE_WHATSAPP_MAIN)
                        replaceFragment(fragmentWhatsAppStatus , bundle)
                    }
                    R.id.messageRecovery->{
                        replaceFragment(FragmentMessageRecovery())
                    }
                }
              true
            }
        }
    }
}

