package com.example.projetmobile4a

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestUser


class LoginActivity : AppCompatActivity() {
    private val api = Rest.getInstance()
    private lateinit var pref: SharedPreferences

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        pref = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        email = pref.getString("email", "")!!
        password = pref.getString("password", "")!!

        api.login(this::loginResponse, null, email, password)
    }

    fun buttonRegisterOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, RegisterActivity::class.java).apply {}
        startActivity(intent)
    }

    fun buttonLoginOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        email = findViewById<EditText>(R.id.textInput_email).text.toString()
        password = findViewById<EditText>(R.id.textInput_password).text.toString()
        api.login(this::loginResponse, null, email, password)
    }

    private fun loginResponse(data: RestUser) {
        if (data.error == null) {
            val editor = pref.edit()
            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()
            editor.commit()

            val intent = Intent(this, MainActivity::class.java).apply {}
            intent.putExtra("USER_ID", data.id)
            intent.putExtra("USER_PSEUDO", data.pseudo)
            startActivity(intent)
            finish()
        } else if (data.error == "already_logged") {
            api.getUser(fun (user: RestUser) {
                if (user.error == null) {
                    val intent = Intent(this, MainActivity::class.java).apply {}
                    intent.putExtra("USER_ID", user.id)
                    intent.putExtra("USER_PSEUDO", user.pseudo)
                    startActivity(intent)
                    finish()
                }
            }, null)
        }
    }
}
