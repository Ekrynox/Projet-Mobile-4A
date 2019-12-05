package com.example.projetmobile4a.view

import com.example.projetmobile4a.model.RestLogin

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.GET

interface RestApi {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<RestLogin>

    @GET("users/logout")
    fun logout()
}