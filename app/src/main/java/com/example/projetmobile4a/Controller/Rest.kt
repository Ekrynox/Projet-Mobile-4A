package com.example.projetmobile4a.Controller

import com.example.projetmobile4a.View.RestApi
import com.google.gson.Gson
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.GsonBuilder



class Rest {
    companion object {
        @JvmStatic private val instance: Rest = Rest()

        @JvmStatic public fun getIntance(): Rest {
            return instance
        }
    }

    private val BASE_URL = "http://192.168.43.225/api/"
    private var gson : Gson? = null
    private var retrofit : Retrofit? = null
    private var gerritAPI : RestApi? = null

    init {
        gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
        gerritAPI = retrofit?.create(RestApi::class.java)
    }
}