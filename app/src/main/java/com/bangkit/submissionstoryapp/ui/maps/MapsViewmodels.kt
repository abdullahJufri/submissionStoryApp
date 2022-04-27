package com.bangkit.submissionstoryapp.ui.maps

import androidx.lifecycle.*
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.data.local.story.StoryRepository
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewmodels(
    private val pref: UserPreference,
    private val storyRepository: StoryRepository
) : ViewModel() {
    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    var listStory: LiveData<List<ListStoryItem>> = _listStory

    fun getUser(): LiveData<Authentication> {
        return pref.getUser().asLiveData()
    }

    fun getLocation(token: String) {
        viewModelScope.launch {
            _listStory.postValue(storyRepository.getLocation(token))
        }
    }

}