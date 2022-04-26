package com.bangkit.submissionstoryapp.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.submissionstoryapp.data.remote.api.ApiConfig
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.data.remote.model.LoginResponse
import com.bangkit.submissionstoryapp.data.remote.model.LoginResult
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.utils.ApiCallbackString
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewmodels(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var _user = MutableLiveData<LoginResult>()

    fun login(email: String, password: String, callback: ApiCallbackString) {
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
                    callback.onResponse(response.body() != null, SUCCESS)

                    val model = Authentication(
                        responseBody?.loginResult!!.name,
                        email,
                        password,
                        responseBody.loginResult.userId,
                        responseBody.loginResult.token,
                        true
                    )
                    saveUser(model)


                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
                callback.onResponse(false, t.message.toString())
            }

        })
    }

    fun saveUser(authentication: Authentication) {
        viewModelScope.launch {
            pref.saveUser(authentication)
        }
    }

    companion object {
        private const val TAG = "loginViewmodel"
        private const val SUCCESS = "success"
    }


}

