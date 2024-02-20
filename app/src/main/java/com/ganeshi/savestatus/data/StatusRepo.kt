package com.ganeshi.savestatus.data

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ganeshi.savestatus.models.MediaModel

class StatusRepo(val context:Context) {

    val whatsAppBusinessStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsAppStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val activity = context as Activity


}