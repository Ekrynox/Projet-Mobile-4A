package com.example.projetmobile4a

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.projetmobile4a.controller.Rest
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val api = Rest.getInstance()
    private lateinit var pref: SharedPreferences

    private var userId = 0
    private var userPseudo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        userId = intent.extras?.getInt("USER_ID") ?: 0
        userPseudo = intent.extras?.getString("USER_PSEUDO") ?: ""

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, DiscussionsFragment(userId, userPseudo))
        transaction.commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }
    }

    private fun onNavigationItemSelected(item: MenuItem) : Boolean {
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

    fun buttonCancelOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        findViewById<TextView>(R.id.textinput_pseudo)?.text = userPseudo
    }

    fun buttonValidateOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        if (findViewById<TextView>(R.id.textinput_pseudo)?.text!!.isNotEmpty()) {
            api.setPseudo({
                if (it.error != null) {
                    findViewById<TextView>(R.id.textinput_pseudo)?.text = userPseudo
                } else {
                    userPseudo = findViewById<TextView>(R.id.textinput_pseudo)?.text.toString()
                }
            }, {
                findViewById<TextView>(R.id.textinput_pseudo)?.text = userPseudo
            }, findViewById<TextView>(R.id.textinput_pseudo)?.text.toString())
        }
    }

    fun buttonLogoutOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
        editor.commit()

        api.logout(null, null)

        val intent = Intent(this, LoginActivity::class.java).apply {}
        startActivity(intent)
        finish()
    }
}
