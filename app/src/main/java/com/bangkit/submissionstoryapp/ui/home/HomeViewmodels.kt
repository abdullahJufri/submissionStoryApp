package com.bangkit.submissionstoryapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.submissionstoryapp.data.remote.api.ApiConfig
import com.bangkit.submissionstoryapp.data.remote.model.*
import com.bangkit.submissionstoryapp.ui.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewmodels : ViewModel() {
    private val _itemStory = MutableLiveData<List<ListStoryItem>>()
    val itemStory: LiveData<List<ListStoryItem>> = _itemStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isHaveData = MutableLiveData<Boolean>()
    val isHaveData: LiveData<Boolean> = _isHaveData

//    private val _snackBarText = MutableLiveData<Event<String>>()
//    val snackBarText: LiveData<Event<String>> = _snackBarText

    fun showListStory(token: String) {
        _isLoading.value = true
        _isHaveData.value = true
        val client = ApiConfig
            .getApiService()
            .getUserStories("Bearer $token")

        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (!responseBody.error) {
                            _itemStory.value = response.body()?.listStory
                            _isHaveData.value = responseBody.message == "Stories fetched successfully"
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
//                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
//                _snackBarText.value = Event(t.message.toString())
            }
        })
    }





    companion object {
        private const val TAG = "ListStoryViewModel"
    }
}