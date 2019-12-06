package com.example.projetmobile4a.view

import com.example.projetmobile4a.model.RestDefault
import com.example.projetmobile4a.model.RestUser

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.GET

interface RestApi {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<RestUser>

    @POST("user/discussions")
    fun getDiscussions() : Call<List<RestUser>>


    @GET("users/logout")
    fun logout() : Call<RestDefault>
}