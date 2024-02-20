package com.ganeshi.savestatus.models
const val MEDIA_TYPE_IMAGE = "image"
const val MEDIA_TYPE_VIDEO = "image"
data class MediaModel(
    val pathUri:String ,
    val fileName:String ,
    val type:String = MEDIA_TYPE_IMAGE ,
    val isDownloaded:Boolean = false
)