package com.bangkit.submissionstoryapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.ui.UserPreference
import kotlinx.coroutines.launch

class MainViewmodels(private val pref: UserPreference) : ViewModel()  {

    fun getUser(): LiveData<Authentication> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}