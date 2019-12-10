package com.example.projetmobile4a

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestUser


class LoginActivity : AppCompatActivity() {
    private var api: Rest? = null
    private var pref: SharedPreferences? = null

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        pref =  getPreferences(Context.MODE_PRIVATE)
        email = pref?.getString("email", "")!!
        password = pref?.getString("password", "")!!

        api = Rest.getInstance()
        api?.login(this::loginResponse, null, email, password)
    }

    fun buttonRegisterOnClick(view: View) {
        val intent = Intent(this, RegisterActivity::class.java).apply {}
        startActivity(intent)
    }

    fun buttonLoginOnClick(view: View) {
        email = findViewById<EditText>(R.id.textinput_email).text.toString()
        password = findViewById<EditText>(R.id.textinput_password).text.toString()
        api?.login(this::loginResponse, null, email, password)
    }

    private fun loginResponse(data: RestUser) {
        if (data.error == null) {
            val editor = pref?.edit()
            editor?.putString("email", email)
            editor?.apply()
            editor?.commit()
            editor?.putString("password", password)
            editor?.apply()
            editor?.commit()

            val intent = Intent(this, MainActivity::class.java).apply {}
            startActivity(intent)
            finish()
            return
        } else if (data.error == "already_logged") {
            val intent = Intent(this, MainActivity::class.java).apply {}
            startActivity(intent)
            finish()
            return
        }
    }
}
