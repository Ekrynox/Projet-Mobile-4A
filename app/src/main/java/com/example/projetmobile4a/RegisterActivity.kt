package com.example.projetmobile4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.projetmobile4a.controller.Rest

class RegisterActivity : AppCompatActivity() {
    private val api = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun buttonRegisterOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        api.register({
            if (it.error == null) {
                finish()
            }
        }, null, findViewById<TextView>(R.id.textinput_email).text.toString(), findViewById<TextView>(R.id.textinput_pseudo).text.toString(), findViewById<TextView>(R.id.textinput_password).text.toString())
    }
}
