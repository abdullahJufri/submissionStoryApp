package com.bangkit.submissionstoryapp.data.remote.api

import com.bangkit.submissionstoryapp.data.remote.model.InfoResponse
import com.bangkit.submissionstoryapp.data.remote.model.LoginResponse
import com.bangkit.submissionstoryapp.data.remote.model.StoriesResponse
import okhttp3.MultipartBody
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

    @Multipart
    @POST("stories")
    fun addStories(
        @Header("Authorization") token: String,
        @Part ("description") des: String,
        @Part file: MultipartBody.Part
    ): Call<InfoResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ): Call<InfoResponse>
}
