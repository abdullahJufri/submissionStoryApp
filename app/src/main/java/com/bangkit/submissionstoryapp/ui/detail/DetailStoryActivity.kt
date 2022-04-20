package com.bangkit.submissionstoryapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem
import com.bangkit.submissionstoryapp.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var story: ListStoryItem
    private lateinit var binding: ActivityDetailStoryBinding
    private val viewmodel: DetailStoryViewmodels by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        story = intent.getParcelableExtra(EXTRA_STORY)!!
        viewmodel.setDetailStory(story)
        displayResult()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayResult() {
        with(binding){
            tvItemName.text = viewmodel.storyItem.name
            tvItemDesc.text = viewmodel.storyItem.description

            Glide.with(imgPhoto)
                .load(viewmodel.storyItem.photoUrl) // URL Avatar
                .placeholder(R.drawable.ic_baseline_image_gray)
                .error(R.drawable.ic_baseline_broken_image_gray)
                .into(imgPhoto)
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}