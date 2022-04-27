package com.bangkit.submissionstoryapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.data.local.story.StoryRepository
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem
import kotlinx.coroutines.launch

class MainViewmodels(private val pref: UserPreference, private val storyRepository: StoryRepository) : ViewModel()  {

    fun getUser(): LiveData<Authentication> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> = storyRepository.getStory(token).cachedIn(viewModelScope)
}