package com.ganeshi.savestatus.viewmodels.factories

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganeshi.savestatus.data.StatusRepo
import com.ganeshi.savestatus.models.MEDIA_TYPE_IMAGE
import com.ganeshi.savestatus.models.MEDIA_TYPE_VIDEO
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.Constants
import com.ganeshi.savestatus.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusViewModel(private val repo: StatusRepo):ViewModel() {
    private val wpBusinessStatusLiveData get() = repo.whatsAppBusinessStatusesLiveData

    // wp business
    val whatsAppBusinessImageData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsAppBusinessVideoData = MutableLiveData<ArrayList<MediaModel>>()
    private var isPermissionGranted = false

    init {
        SharedPrefUtils.init(repo.context)
        val wpBusinessPermission = SharedPrefUtils.getPrefBoolean(SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED , false)
        isPermissionGranted = wpBusinessPermission
        if (isPermissionGranted){
            CoroutineScope(Dispatchers.IO).launch {
                repo.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }
        }
    }
    fun  getWhatsAppStatus(){
        CoroutineScope(Dispatchers.IO).launch {
            if (!isPermissionGranted){
                repo.getAllStatuses()
            }
            withContext(Dispatchers.Main){
                getWhatsAppBusinessImage()
                getWhatsAppBusinessVideo()
            }

        }
    }

    fun getWhatsAppBusinessImage(){
       wpBusinessStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach{mediaModel ->
                if (mediaModel.type == MEDIA_TYPE_IMAGE){
                    tempList.add(mediaModel)
                }
            }
           whatsAppBusinessImageData.postValue(tempList)
        }

    }

    fun  getWhatsAppBusinessVideo(){
        wpBusinessStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach{mediaModel ->
                if (mediaModel.type == MEDIA_TYPE_VIDEO){
                    tempList.add(mediaModel)
                }
            }
            whatsAppBusinessVideoData.postValue(tempList)
        }

    }


}