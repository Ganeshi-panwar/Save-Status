package com.ganeshi.savestatus.data

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.room.util.query
import com.ganeshi.savestatus.models.MEDIA_TYPE_IMAGE
import com.ganeshi.savestatus.models.MEDIA_TYPE_VIDEO
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.Constants
import com.ganeshi.savestatus.utils.SharedPrefUtils
import com.ganeshi.savestatus.utils.getFileExtension
import com.ganeshi.savestatus.utils.isStatusExit
import java.io.File
import java.util.concurrent.TimeUnit

class StatusRepo(val context:Context) {

    val whatsAppBusinessStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val activity = context as Activity
//    private val wpBusinessStatusList = ArrayList<MediaModel>()
    fun getAllStatuses(whatsAppType: String = Constants.TYPE_WHATSAPP_BUSINESS) {
        val treeUri = when (whatsAppType) {
            Constants.TYPE_WHATSAPP_BUSINESS -> {
                SharedPrefUtils.getPrefString(
                    SharedPrefUtils
                        .SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, ""
                )?.toUri()
            }
            else -> null
        }
        treeUri?.let { uri ->
            try {
//
//                val collection =
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                        MediaStore.Video.Media.getContentUri(
//                            MediaStore.VOLUME_EXTERNAL
//                        )
//                    } else {
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//                    }

                activity.contentResolver.takePersistableUriPermission(
                    treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                val fileDocument = DocumentFile.fromTreeUri(activity, uri)
                val wpBusinessStatusList = ArrayList<MediaModel>()
                fileDocument?.listFiles()?.forEach { file ->
                    if (file.isFile && file.name != ".nomedia") {
                        val isDownload = context.isStatusExit(file.name!!)
                        val type = if (getFileExtension(file.name!!) == "mp4") {
                            MEDIA_TYPE_VIDEO
                        } else {
                            MEDIA_TYPE_IMAGE
                        }
                        val model = MediaModel(
                            pathUri = file.uri.toString(),
                            fileName = file.name!!,
                            type = type,
                            isDownloaded = isDownload
                        )
                        Log.d("file", file.toString())


                        wpBusinessStatusList.add(model)
                        Log.d("modelList" , model.toString())
                    }
                }
                whatsAppBusinessStatusesLiveData.postValue(wpBusinessStatusList)
            } catch (e: SecurityException) {
                e.printStackTrace()

            }
        }
    }
}

//                fileDocument?.let {
//                    it.listFiles().forEach { file ->
//                        if (file.name != ".nomedia" && file.isFile) {
//                            val isDownloaded = context.isStatusExit(file.name!!)
//                            val type = if (getFileExtension(file.name!!) == "mp4") {
//                                MEDIA_TYPE_VIDEO
//                            } else {
//                                MEDIA_TYPE_IMAGE
//                            }
//                            val model = MediaModel(
//                                pathUri = file.uri.toString(),
//                                fileName = file.name!!,
//                                type = type,
//                                isDownloaded = isDownloaded
//                            )
//                            when (whatsAppType) {
//                                Constants.TYPE_WHATSAPP_BUSINESS -> {
//                                    wpBusinessStatusList.add(model)
//                                }
//                            }
//                        }
//                    }
//
//                    when (whatsAppType) {
//                        Constants.TYPE_WHATSAPP_BUSINESS -> {
//                            whatsAppBusinessStatusesLiveData.postValue(wpBusinessStatusList)
//                        }
//                    }
//                }
//            }
//        }
//    }

