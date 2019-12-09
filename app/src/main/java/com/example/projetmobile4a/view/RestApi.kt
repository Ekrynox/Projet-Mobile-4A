package com.example.projetmobile4a.view

import com.example.projetmobile4a.model.RestDefault
import com.example.projetmobile4a.model.RestUser
import com.example.projetmobile4a.model.RestUsersList

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.GET
import java.util.*

interface RestApi {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<RestUser>

    @GET("users/logout")
    fun logout() : Call<RestDefault>

    @GET("users/discussions")
    fun getDiscussions() : Call<RestUsersList>


    @GET("friends")
    fun getFriends() : Call<RestUsersList>
}