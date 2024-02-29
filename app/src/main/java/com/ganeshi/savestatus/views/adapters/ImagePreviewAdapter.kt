package com.ganeshi.savestatus.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.databinding.ItemImagePreviewBinding
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.saveStatus
import com.ganeshi.savestatus.views.activities.ImagePreview

class ImagePreviewAdapter(private val list: ArrayList<MediaModel> ,  val context: Context):
    RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder>() {
        inner class ViewHolder(private val binding:ItemImagePreviewBinding):RecyclerView.ViewHolder(binding.root){
            fun bind(mediaModel:MediaModel){
                binding.apply {
                    Glide.with(context)
                        .load(zoomableImageView)
                    val downloadImage = if (mediaModel.isDownloaded){
                        R.drawable.ic_downloaded
                    }else{
                        R.drawable.ic_download
                    }
                    tools.statusDownload1.setImageResource(downloadImage)

                    tools.statusDownload1.setOnClickListener{
                        val isDownloaded = context.saveStatus(mediaModel)
                        if(isDownloaded){
                            Toast.makeText(context , "Saved" , Toast.LENGTH_SHORT).show()
                            mediaModel.isDownloaded = true
                        }else{
                            Toast.makeText(context , "Unable to Save" , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ImagePreviewAdapter.ViewHolder {
        return ViewHolder(ItemImagePreviewBinding.inflate(LayoutInflater.from(context) , parent , false))

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val model: MediaModel = list[position]
        holder.bind(model)
    }
}