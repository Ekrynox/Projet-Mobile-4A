package com.example.projetmobile4a.controller

import com.example.projetmobile4a.model.RestLogin
import com.example.projetmobile4a.view.RestApi

import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import okhttp3.JavaNetCookieJar
import okhttp3.logging.HttpLoggingInterceptor

import java.net.CookieHandler
import java.net.CookieManager


class Rest {
    companion object {
        @JvmStatic private val instance: Rest = Rest()

        @JvmStatic public fun getInstance(): Rest {
            return instance
        }
    }

    private val apiURL = "https://hollywood-messenger.glitch.me/api/"
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


    fun login(success: ((RestLogin) -> Unit)?, failure: (() -> Unit)?, email: String, password: String) {
        val call = gerritAPI?.login(email, password)
        call?.enqueue(RestCallBack<RestLogin>(success, failure))
    }
}