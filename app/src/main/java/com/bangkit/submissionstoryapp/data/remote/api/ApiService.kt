@file:Suppress("unused")

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

    @Multipart
    @POST("stories")
    fun addStories(
        @Header("Authorization") token: String,
        @Part("description") des: String,
        @Part file: MultipartBody.Part,
        @Part("lat") lat: Float,
        @Part("lon") lon: Float
    ): Call<InfoResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ): Call<InfoResponse>


    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ): StoriesResponse


    @GET("stories?location=1")
    suspend fun getStoriesLocation(
        @Header("Authorization") token: String
    ): StoriesResponse


}

