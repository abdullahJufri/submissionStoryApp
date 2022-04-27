package com.bangkit.submissionstoryapp.data.local.di

import android.content.Context
import com.bangkit.submissionstoryapp.data.local.database.StoryDatabase
import com.bangkit.submissionstoryapp.data.local.story.StoryRepository
import com.bangkit.submissionstoryapp.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}