package com.example.projetmobile4a

import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    private var api : Rest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        api = Rest.getInstance()
    }

    fun buttonRegisterOnClick(view: View) {
        val intent = Intent(this, RegisterActivity::class.java).apply {}
        startActivity(intent)
    }

    fun buttonLoginOnClick(view: View) {
        api?.login(this::loginResponse, null, findViewById<EditText>(R.id.textinput_email).text.toString(), findViewById<EditText>(R.id.textinput_password).text.toString())
    }

    private fun loginResponse(data: RestUser) {
        if (data.error == null) {
            Log.d(this.toString(), data.pseudo)

            val intent = Intent(this, MainActivity::class.java).apply {}
            startActivity(intent)
            finish()
            return
        }

        Log.d(this.toString(), data.error)
    }
}
