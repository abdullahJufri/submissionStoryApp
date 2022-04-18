package com.bangkit.submissionstoryapp.ui.detail

import android.graphics.text.LineBreaker
import android.os.Build
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


    private val vm: DetailStoryViewmodels by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set text view description to justify
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            run {
//                binding.tvDescription.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
//            }
//        }

        story = intent.getParcelableExtra(EXTRA_STORY)!!
        vm.setDetailStory(story)
        displayResult()
//        setupToolbar()
    }

//    private fun setupToolbar(){
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayResult() {
        with(binding){
            tvName.text = vm.storyItem.name
//            tvCreatedTime.text = getString(R.string.created_add, vm.storyItem.createdAt)
//            tvDescription.text = vm.storyItem.description

            Glide.with(ivStory)
                .load(vm.storyItem.photoUrl) // URL Avatar
//                .placeholder(R.drawable.ic_place_holder)
//                .error(R.drawable.ic_broken_image)
                .into(ivStory)
        }
    }

    companion object {
        const val EXTRA_STORY = "story"
    }
}