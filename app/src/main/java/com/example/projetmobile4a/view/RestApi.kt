package com.example.projetmobile4a.view

import com.example.projetmobile4a.model.*
import retrofit2.Call
import retrofit2.http.*

interface RestApi {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<RestUser>

    @GET("auth/logout")
    fun logout() : Call<RestDefault>


    @GET("users")
    fun getUser() : Call<RestUser>

    @GET("users/search/{filter}")
    fun searchUsers(@Path("filter") filter : String) : Call<RestUsersList>

    @GET("users/{id}")
    fun getUserById(@Path("id") id : Int) : Call<RestUser>

    @GET("users/discussions")
    fun getDiscussions() : Call<RestUsersList>

    @FormUrlEncoded
    @PUT("users")
    fun setPseudo(@Field("pseudo") pseudo: String) : Call<RestDefault>

    @FormUrlEncoded
    @POST("users")
    fun register(@Field("email") email : String, @Field("pseudo") pseudo : String, @Field("password") password : String) : Call<RestDefault>


    @GET("messagesGroups/{id}")
    fun getMessagesGroups(@Path("id") id : Int) : Call<RestMessageList>

    @FormUrlEncoded
    @POST("messagesGroups")
    fun addMessagesGroups(@Field("id") id: Int, @Field("data") data: String) : Call<RestDefault>


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


    @GET("groups")
    fun getGroups() : Call<RestGroupsList>

    @GET("groups/{id}")
    fun getGroupById(@Path("id") id : Int) : Call<RestGroup>

    @DELETE("groups/{id}/users/{userId}")
    fun removeUserFromGroup(@Path("id") id : Int, @Path("userId") userId : Int) : Call<RestDefault>
}