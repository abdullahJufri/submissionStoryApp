package com.bangkit.submissionstoryapp.ui.addstory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submissionstoryapp.data.remote.api.ApiConfig
import com.bangkit.submissionstoryapp.data.remote.model.InfoResponse
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.utils.ApiCallbackString

import okhttp3.MultipartBody
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewmodels : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadImage(
        user: Authentication,
        description: String,
        imageMultipart: MultipartBody.Part,
        callback: ApiCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig().getApiService()
            .addStories("Bearer ${user.token}", description, imageMultipart)
        client.enqueue(object : Callback<InfoResponse> {
            override fun onResponse(
                call: Call<InfoResponse>,
                response: Response<InfoResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        callback.onResponse(response.body() != null, SUCCESS)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                    // get message error
                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                callback.onResponse(false, t.message.toString())
            }
        })

    }

    companion object {
        private const val TAG = "addStoryViewmodel"
        private const val SUCCESS = "success"
    }
}