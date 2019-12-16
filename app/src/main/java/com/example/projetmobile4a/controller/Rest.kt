package com.example.projetmobile4a.controller


import com.example.projetmobile4a.GlobalApplication
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

    private var sql: SQL? = null

    init {
        interceptor = HttpLoggingInterceptor()
        //interceptor?.level = HttpLoggingInterceptor.Level.BODY
        cookie = CookieManager()
        client = OkHttpClient.Builder().addNetworkInterceptor(interceptor!!).cookieJar(JavaNetCookieJar(cookie!!)).build()

        gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder().baseUrl(apiURL).addConverterFactory(GsonConverterFactory.create(gson)).client(client!!).build()
        gerritAPI = retrofit?.create(RestApi::class.java)

        if (GlobalApplication.getAppContext() != null) {
            sql = SQL.getInstance(GlobalApplication.getAppContext()!!)
        }
    }

    inner class RestCallBack<T>(private val success: ((T) -> Unit)?, private val failure: (() -> Unit)?, private val sqlSet : ((T) -> Unit)? = null, private val sqlGet: (() -> T?)? = null) :
        Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                sqlSet?.invoke(response.body())
                success?.invoke(response.body())
            } else {
                println(response.errorBody())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            if (sqlGet == null) {
                //t.printStackTrace()
                failure?.invoke()
            } else {
                val res = sqlGet.invoke()
                if (res == null) {
                    //t.printStackTrace()
                    failure?.invoke()
                } else {
                    success?.invoke(res)
                }
            }
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

    private var userId = 0
    fun getUser(success: ((RestUser) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getUser()
        call?.enqueue(RestCallBack<RestUser>(success, failure, {
            if (it.error == null) {
                userId = it.id!!
                sql?.sql()?.addUser(it)
            }
        }, {
            return@RestCallBack sql?.sql()?.getUser(userId)
        }))
    }

    fun getUserById(success: ((RestUser) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.getUserById(id)
        call?.enqueue(RestCallBack<RestUser>(success, failure, {
            if (it.error == null) {
                sql?.sql()?.addUser(it)
            }
        }, {
            return@RestCallBack sql?.sql()?.getUser(id)
        }))
    }

    private var discussions = ArrayList<Int>()
    fun getDiscussions(success: ((RestUsersList) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getDiscussions()
        call?.enqueue(RestCallBack<RestUsersList>(success, failure, {
            if (it.error == null) {
                discussions = ArrayList()
                for (user in it.users!!) {
                    sql?.sql()?.addUser(user)
                    discussions.add(user.id!!)
                }
            }
        }, {
            val users = ArrayList<RestUser>()
            val res = RestUsersList()
            for (id in discussions) {
                val user = sql?.sql()?.getUser(id)
                if (user != null) {
                    users.add(user)
                }
            }
            res.users = users.toList()
            return@RestCallBack res
        }))
    }


    fun getMessages(success: ((RestMessageList) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.getMessages(id)
        call?.enqueue(RestCallBack<RestMessageList>(success, failure))
    }

    fun addMessages(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int, data: RestMessageData) {
        val call = gerritAPI?.addMessages(id, gson!!.toJson(data))
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }


    fun getMessagesGroups(success: ((RestMessageList) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.getMessagesGroups(id)
        call?.enqueue(RestCallBack<RestMessageList>(success, failure))
    }

    fun addMessagesGroups(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int, data: RestMessageData) {
        val call = gerritAPI?.addMessagesGroups(id, gson!!.toJson(data))
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }


    private var friends = ArrayList<Int>()
    fun getFriends(success: ((RestUsersList) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getFriends()
        call?.enqueue(RestCallBack<RestUsersList>(success, failure,{
            if (it.error == null) {
                friends = ArrayList()
                for (user in it.users!!) {
                    sql?.sql()?.addUser(user)
                    friends.add(user.id!!)
                }
            }
        }, {
            val users = ArrayList<RestUser>()
            val res = RestUsersList()
            for (id in friends) {
                val user = sql?.sql()?.getUser(id)
                if (user != null) {
                    users.add(user)
                }
            }
            res.users = users.toList()
            return@RestCallBack res
        }))
    }

    fun addFriend(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.addFriend(id)
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }

    fun removeFriend(success: ((RestDefault) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.removeFriend(id)
        call?.enqueue(RestCallBack<RestDefault>(success, failure))
    }


    private var groups = ArrayList<Int>()
    fun getGroups(success: ((RestGroupsList) -> Unit)?, failure: (() -> Unit)?) {
        val call = gerritAPI?.getGroups()
        call?.enqueue(RestCallBack<RestGroupsList>(success, failure, {
            if (it.error == null) {
                groups = ArrayList()
                for (group in it.groups!!) {
                    val usersId = ArrayList<Int>()
                    for (user in group.users!!) {
                        usersId.add(user.id!!)
                        sql?.sql()?.addUser(user)
                    }
                    group.usersId = usersId.toList()
                    sql?.sql()?.addGroup(group)
                    groups.add(group.id!!)
                }
            }
        }, {
            val groupsList = ArrayList<RestGroup>()
            val res = RestGroupsList()
            for (id in groups) {
                val group = sql?.sql()?.getGroup(id)
                val users = ArrayList<RestUser>()
                for (userId in group?.usersId!!) {
                    val user = sql?.sql()?.getUser(userId)
                    user != null && users.add(user)
                }
                group.users = users.toList()
                groupsList.add(group)
            }
            res.groups = groupsList.toList()
            return@RestCallBack res
        }))
    }

    fun getGroupById(success: ((RestGroup) -> Unit)?, failure: (() -> Unit)?, id: Int) {
        val call = gerritAPI?.getGroupById(id)
        call?.enqueue(RestCallBack<RestGroup>(success, failure, {
            if (it.error == null) {
                sql?.sql()?.addGroup(it)
            }
        }, {
            return@RestCallBack sql?.sql()?.getGroup(id)
        }))
    }
}