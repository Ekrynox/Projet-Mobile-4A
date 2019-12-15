package com.example.projetmobile4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var userId = 0
    private var userPseudo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.extras?.getInt("USER_ID") ?: 0
        userPseudo = intent.extras?.getString("USER_PSEUDO") ?: ""

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, DiscussionsFragment(userId, userPseudo))
        transaction.commit()
    }

}
