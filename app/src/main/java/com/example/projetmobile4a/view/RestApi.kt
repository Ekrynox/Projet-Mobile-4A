package com.example.projetmobile4a.view

import com.example.projetmobile4a.model.RestDefault
import com.example.projetmobile4a.model.RestMessageList
import com.example.projetmobile4a.model.RestUser
import com.example.projetmobile4a.model.RestUsersList

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface RestApi {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<RestUser>

    @GET("users/logout")
    fun logout() : Call<RestDefault>


    @GET("user")
    fun getUser() : Call<RestUser>

    @GET("user/{id}")
    fun getUserById(@Path("id") id : Number) : Call<RestUser>

    @GET("users/discussions")
    fun getDiscussions() : Call<RestUsersList>


    @GET("messages/{id}")
    fun getMessages(@Path("id") id : Number) : Call<RestMessageList>


    @GET("friends")
    fun getFriends() : Call<RestUsersList>

    @FormUrlEncoded
    @POST("friends")
    fun addFriend(@Field("id") id : Number) : Call<RestDefault>

    @DELETE("friends/{id}")
    fun removeFriend(@Path("id") id : Number) : Call<RestDefault>
}