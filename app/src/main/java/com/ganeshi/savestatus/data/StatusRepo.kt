package com.ganeshi.savestatus.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.ganeshi.savestatus.models.MEDIA_TYPE_IMAGE
import com.ganeshi.savestatus.models.MEDIA_TYPE_VIDEO
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.Constants
import com.ganeshi.savestatus.utils.SharedPrefUtils
import com.ganeshi.savestatus.utils.getFileExtension
import com.ganeshi.savestatus.utils.isStatusExit
import java.io.File

class StatusRepo(val context:Context) {

    val whatsAppBusinessStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    private val whatsAppStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()

    val activity = context as Activity
    private val wpStatusList = ArrayList<MediaModel>()
    private val wpBusinessStatusList = ArrayList<MediaModel>()

    fun getAllStatuses(whatsAppType:String = Constants.TYPE_WHATSAPP_BUSINESS ){
        val treeUri = when(whatsAppType){
            Constants.TYPE_WHATSAPP_BUSINESS->{
                SharedPrefUtils.getPrefString(SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, "")?.toUri()!!
            }
            else->{
                SharedPrefUtils.getPrefString(SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, "")?.toUri()!!
//                SharedPrefUtils.getPrefString(SharedPrefUtils.SharedPrefKeys.PREF_KEY_WP_TREE_URI, "")?.toUri()!!
           }
        }
        activity.contentResolver.takePersistableUriPermission(
            treeUri , Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        val fileDocument = DocumentFile.fromTreeUri(activity  , treeUri)
        fileDocument?.let {
            it.listFiles().forEach { file->
                if (file.name != ".nomedia" && file.isFile){
                    val isDownloaded = context.isStatusExit(file.name!!)
                    val type = if(getFileExtension(file.name!!) == "mp4"){
                        MEDIA_TYPE_VIDEO
                    }
                    else{
                        MEDIA_TYPE_IMAGE
                    }
                    val model = MediaModel(
                        pathUri = file.uri.toString(),
                        fileName = file.name!!,
                        type = type,
                        isDownloaded = isDownloaded

                    )
                    when(whatsAppType){
                        Constants.TYPE_WHATSAPP_BUSINESS ->{
                            wpBusinessStatusList.add(model)
                        }
//                        else ->{
//                            wpStatusList.add(model)
//                        }
                    }

                }
            }

            when(whatsAppType){
                Constants.TYPE_WHATSAPP_BUSINESS ->{
                    whatsAppBusinessStatusesLiveData.postValue(wpBusinessStatusList)
                }
//                else ->{
//                    whatsAppStatusesLiveData.postValue(wpStatusList)
//                }
            }
        }

    }

}