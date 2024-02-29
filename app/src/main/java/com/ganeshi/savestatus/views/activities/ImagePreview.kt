package com.ganeshi.savestatus.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ganeshi.savestatus.R
import com.ganeshi.savestatus.databinding.ActivityImagePreviewBinding
import com.ganeshi.savestatus.models.MediaModel
import com.ganeshi.savestatus.utils.Constants
import com.ganeshi.savestatus.views.adapters.ImagePreviewAdapter

class ImagePreview : AppCompatActivity() {
    private val binding by lazy {
        ActivityImagePreviewBinding.inflate(layoutInflater)
    }
     lateinit var adapter: ImagePreviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.apply {
            val list = intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY , 0)
            adapter = ImagePreviewAdapter(list , this@ImagePreview)
            imageViewPager.adapter = adapter
            imageViewPager.currentItem = scrollTo


        }
    }
}