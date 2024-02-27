package com.ganeshi.savestatus.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.databinding.ItemMediaBinding
import com.ganeshi.savestatus.models.MEDIA_TYPE_IMAGE
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.saveStatus

class MediaAdapter(private val list:ArrayList<MediaModel>, val context:Context):
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(mediaModel: MediaModel) {
            binding.apply {
                Glide.with(context)
                    .load(mediaModel.pathUri.toUri())
                    .into(statusImage)
                if (mediaModel.type == MEDIA_TYPE_IMAGE){
                    statusPlay.visibility = View.GONE
                }
                val downloadedImage = if (mediaModel.isDownloaded){
                    R.drawable.ic_downloaded
                }else{
                    R.drawable.ic_download
                }
                statusDownload.setImageResource(downloadedImage)
                cardStatus.setOnClickListener{
                    if(mediaModel.type == MEDIA_TYPE_IMAGE){
                        // goto image preview activity

                    }
                    else{
                        // goto video preview activity

                    }
                }
                statusDownload.setOnClickListener{
                    val isDownload = context.saveStatus(mediaModel)
                    if (isDownload){
                        // status is downloaded
                        Toast.makeText(context , "Saved" , Toast.LENGTH_SHORT).show()
                        mediaModel.isDownloaded = true
                        statusDownload.setImageResource(R.drawable.ic_downloaded)
                    }else{
                        // unable to status download
                        Toast.makeText(context , "Unable to save" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(context), parent , false))
    }
    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)

    }
}