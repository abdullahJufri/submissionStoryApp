package com.bangkit.submissionstoryapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.submissionstoryapp.ui.Authentication
import com.bangkit.submissionstoryapp.ui.UserPreference
import kotlinx.coroutines.launch

//class ShareUserViewmodels(private val pref: UserPreference) : ViewModel() {
//    fun getUser() : LiveData<Authentication> {
//        return pref.getUser().asLiveData()
//    }
//
//    fun saveUser(user: Authentication) {
//        viewModelScope.launch {
//            pref.saveUser(user)
//        }
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            pref.logout()
//        }
//    }
//}