package com.bangkit.submissionstoryapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.ui.login.LoginViewmodels
import com.bangkit.submissionstoryapp.ui.main.MainViewmodels


class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewmodels::class.java) -> {
                MainViewmodels(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewmodels::class.java) -> {
                LoginViewmodels(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}