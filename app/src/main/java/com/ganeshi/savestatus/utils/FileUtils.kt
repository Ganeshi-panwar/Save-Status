package com.ganeshi.savestatus.utils

import android.content.Context
import android.os.Environment
import com.ganeshi.savestatus.R
import java.io.File

fun Context.isStatusExit(fileName:String):Boolean{
    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File("${downloadDir}/${getString(R.string.app_name)} " , fileName)
    return  file.exists()
}

fun getFileExtension(fileName:String):String{
    val lastDotIndex = fileName.lastIndexOf(".")
    if (lastDotIndex > 0 && lastDotIndex <fileName.length -1){
        return  fileName.substring(lastDotIndex-1)
    }
    return ""
}