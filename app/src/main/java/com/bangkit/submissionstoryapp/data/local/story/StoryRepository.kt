package com.bangkit.submissionstoryapp.data.local.story

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.bangkit.submissionstoryapp.data.local.database.StoryDatabase
import com.bangkit.submissionstoryapp.data.remote.api.ApiService
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem

class StoryRepository (private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, "Bearer $token"),
            pagingSourceFactory = {
//                StoryPagingSource(apiService, token)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getLocation(token: String): List<ListStoryItem> {
        return apiService.getStoriesLocation("bearer $token").listStory as List<ListStoryItem>
    }
}