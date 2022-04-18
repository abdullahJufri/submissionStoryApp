package com.bangkit.submissionstoryapp.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Authentication(
    val token: String,
    val isLogin: Boolean
) : Parcelable