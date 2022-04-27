package com.bangkit.submissionstoryapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM storiesTable")
    fun getAllLocalStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM storiesTable")
    suspend fun deleteAll()
}