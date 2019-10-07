package com.example.projetmobile4a.View

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.GET

interface RestApi {
    @FormUrlEncoded
    @POST("users/login")
    fun login(@Field("email") email : String, @Field("password") password : String)

    @POST("users/logout")
    @GET("users/logout")
    fun logout()
}