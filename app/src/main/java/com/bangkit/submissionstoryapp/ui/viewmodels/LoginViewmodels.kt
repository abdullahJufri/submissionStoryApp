package com.bangkit.submissionstoryapp.ui.viewmodels

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.bangkit.submissionstoryapp.data.remote.api.ApiConfig
import com.bangkit.submissionstoryapp.data.remote.model.LoginResponse
import com.bangkit.submissionstoryapp.data.remote.model.LoginResult
import com.bangkit.submissionstoryapp.data.remote.model.User
import com.bangkit.submissionstoryapp.data.utils.Event
import com.bangkit.submissionstoryapp.ui.Authentication
import com.bangkit.submissionstoryapp.ui.UserPreference
import com.bangkit.submissionstoryapp.utils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewmodels(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var _user = MutableLiveData<LoginResult>()
    val user: LiveData<LoginResult> = _user

    fun login(email: String, password: String,callback: utils.ApiCallbackString) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.e("AuthenticationViewModel", "onResponse: " + response.body())
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
//              _user.value = response.body()?.loginResult

                    callback.onResponse(response.body() != null, SUCCESS)

                    val model = User(
                        responseBody?.loginResult!!.name,
                        email,
                        password,
                        responseBody.loginResult.userId,
                        responseBody.loginResult.token,
                        true
                    )
                    saveUser(model)


                } else {
                    Log.e("AuthenticationViewModel", "onResponse fail: ")
//                    _message.value = Event(response.message())
//                    _error.value = Event(true)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    companion object {
        private const val TAG = "SignInViewModel"
        private const val SUCCESS = "success"
    }



}

