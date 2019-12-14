package com.example.projetmobile4a.controller

import com.example.projetmobile4a.model.*
import com.example.projetmobile4a.view.RestApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager


class Rest {
    companion object {
        @JvmStatic private val instance: Rest = Rest()

        @JvmStatic fun getInstance(): Rest {
            return instance
        }
    }

    private val apiURL = "https://hollywood-messenger-mobile.glitch.me/api/"
    private var gson : Gson? = null
    private var retrofit : Retrofit? = null
    private var gerritAPI : RestApi? = null

    private var interceptor : HttpLoggingInterceptor? = null
    private var cookie : CookieHandler? = null
    private var client : OkHttpClient? = null

    init {
        interceptor = HttpLoggingInterceptor()
        interceptor?.level = HttpLoggingInterceptor.Level.BODY
        cookie = CookieManager()
        client = OkHttpClient.Builder().addNetworkInterceptor(interceptor!!).cookieJar(JavaNetCookieJar(cookie!!)).build()

        gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder().baseUrl(apiURL).addConverterFactory(GsonConverterFactory.create(gson)).client(client!!).build()
        gerritAPI = retrofit?.create(RestApi::class.java)
    }

    inner class RestCallBack<T>(private val success: ((T) -> Unit)?, private val failure: (() -> Unit)?) :
        Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                success?.invoke(response.body())
            } else {
                println(response.errorBody())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            t.printStackTrace()
            failure?.invoke()
        }
    }


    fun login(success: ((RestUser) -> Unit)?, failure: (() -> Unit)?, email: String, password: String) {
        val call = gerritAPI?.login(email, password)
        call?.enqueue(RestCallBack<RestUser>(success, failure))
    }

    fun logout(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.logout()
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }


    fun getUser(success: ((RestUser) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getUser()
        call?.enqueue(RestCallBack<RestUser>(success, failure))
    }

    fun getUserById(success: ((RestUser) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.getUserById(id)
        call?.enqueue(RestCallBack<RestUser>(success, failure))
    }

    fun getDiscussions(success: ((RestUsersList) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getDiscussions()
        call?.enqueue(RestCallBack<RestUsersList>(success, failure))
    }


    fun getMessages(success: ((RestMessageList) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.getMessages(id)
        call?.enqueue(RestCallBack<RestMessageList>(success, failure))
    }

    fun addMessages(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int, data: RestMessageData) {
        val call = gerritAPI?.addMessages(id, gson!!.toJson(data))
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }


    fun getFriends(success: ((RestUsersList) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getFriends()
        call?.enqueue(RestCallBack<RestUsersList>(success, failure))
    }

    fun addFriend(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.addFriend(id)
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }

    fun removeFriend(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.removeFriend(id)
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }
}