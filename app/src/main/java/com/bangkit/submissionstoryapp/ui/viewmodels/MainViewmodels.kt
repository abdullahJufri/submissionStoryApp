package com.bangkit.submissionstoryapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.submissionstoryapp.data.remote.model.User
import com.bangkit.submissionstoryapp.ui.UserPreference
import kotlinx.coroutines.launch

class MainViewmodels(private val pref: UserPreference) : ViewModel()  {

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}