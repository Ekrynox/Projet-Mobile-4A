package com.example.projetmobile4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.core.view.get
import com.example.projetmobile4a.controller.Rest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var userId = 0
    private var userPseudo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.extras?.getInt("USER_ID") ?: 0
        userPseudo = intent.extras?.getString("USER_PSEUDO") ?: ""

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, MessagesFragment(userId, userPseudo))
        transaction.commit()
    }

}
