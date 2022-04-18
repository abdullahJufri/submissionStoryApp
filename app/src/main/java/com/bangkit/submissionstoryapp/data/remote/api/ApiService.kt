package com.bangkit.submissionstoryapp.data.remote.api

import com.bangkit.submissionstoryapp.data.remote.model.LoginResponse
import com.bangkit.submissionstoryapp.data.remote.model.StoriesResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("stories")
    fun getUserStories(
        @Header("Authorization") token: String
    ) : Call<StoriesResponse>
}
