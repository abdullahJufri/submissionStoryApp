package com.bangkit.submissionstoryapp.ui.detail

import androidx.lifecycle.ViewModel
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem

class DetailStoryViewmodels : ViewModel() {
    lateinit var storyItem: ListStoryItem

    fun setDetailStory(story: ListStoryItem) : ListStoryItem{
        storyItem = story
        return storyItem
    }

}