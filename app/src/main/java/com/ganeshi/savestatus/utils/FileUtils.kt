package com.ganeshi.savestatus.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.models.MediaModel
import java.io.File

fun Context.isStatusExit(fileName:String):Boolean{
    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File("${downloadDir}/${getString(R.string.app_name)} " , fileName)
    return  file.exists()
}

fun getFileExtension(fileName:String):String{
    val lastDotIndex = fileName.lastIndexOf(".")
    if (lastDotIndex >= 0 && lastDotIndex <fileName.length -1){
        return  fileName.substring(lastDotIndex +1)
    }
    return ""
}

fun Context.saveStatus(model:MediaModel):Boolean{
    if (isStatusExit(model.fileName)){
        return true
    }

    val extension = getFileExtension(model.fileName)
    val mimeType = "${model.type} $extension"
    val inputStream = contentResolver.openInputStream(model.pathUri.toUri())
    try {
        val values = ContentValues()
        values.apply {
            put(MediaStore.MediaColumns.MIME_TYPE , mimeType)
            put(MediaStore.MediaColumns.DISPLAY_NAME , model.fileName)
            put(
                MediaStore.MediaColumns.RELATIVE_PATH ,
                Environment.DIRECTORY_DOCUMENTS + "/" + getString(R.string.app_name)
            )
        }
        val uri = contentResolver.insert(
            MediaStore.Files.getContentUri("external") , values
        )
        uri?.let {
            val outputStream = contentResolver.openOutputStream(it)
            if (inputStream != null){
                outputStream?.write(inputStream.readBytes())
            }
            outputStream?.close()
            inputStream?.close()
            return true
        }
    }catch (e: Exception){
        e.printStackTrace()
        Log.d("Error" , "catch error ${e.toString()}")
    }
    return  false
}