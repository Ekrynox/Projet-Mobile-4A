package com.example.projetmobile4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }
    }

    private fun onNavigationItemSelected(item: MenuItem) : Boolean {
        println(item.itemId)
        println(R.id.settings)
        when (item.itemId) {
            R.id.messages -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment, DiscussionsFragment(userId, userPseudo))
                transaction.commit()
            }
            R.id.settings -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment, SettingsFragment(userId, userPseudo))
                transaction.commit()
            }
        }
        return true
    }
}
