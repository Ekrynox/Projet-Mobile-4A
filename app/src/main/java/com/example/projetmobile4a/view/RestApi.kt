package com.example.projetmobile4a.view

import com.example.projetmobile4a.model.*

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface RestApi {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<RestUser>

    @GET("auth/logout")
    fun logout() : Call<RestDefault>


    @GET("users")
    fun getUser() : Call<RestUser>

    @GET("users/{id}")
    fun getUserById(@Path("id") id : Int) : Call<RestUser>

    @GET("users/discussions")
    fun getDiscussions() : Call<RestUsersList>


    @GET("messages/{id}")
    fun getMessages(@Path("id") id : Int) : Call<RestMessageList>

    @FormUrlEncoded
    @POST("messages")
    fun addMessages(@Field("id") id: Int, @Field("data") data: String) : Call<RestDefault>


    @GET("friends")
    fun getFriends() : Call<RestUsersList>

    @FormUrlEncoded
    @POST("friends")
    fun addFriend(@Field("id") id : Int) : Call<RestDefault>

    @DELETE("friends/{id}")
    fun removeFriend(@Path("id") id : Int) : Call<RestDefault>
}