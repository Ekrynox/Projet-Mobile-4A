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
import com.example.projetmobile4a.mainActivityFragments.ContactsFragment
import com.example.projetmobile4a.mainActivityFragments.DiscussionsFragment
import com.example.projetmobile4a.mainActivityFragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val api = Rest.getInstance()
    private lateinit var pref: SharedPreferences

    private var userId = 0
    private var userPseudo = ""

    private lateinit var discussionsFragment: DiscussionsFragment
    private lateinit var contactsFragment: ContactsFragment
    private lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        userId = intent.extras?.getInt("USER_ID") ?: 0
        userPseudo = intent.extras?.getString("USER_PSEUDO") ?: ""

        discussionsFragment = DiscussionsFragment(userId, userPseudo)
        contactsFragment = ContactsFragment(userId, userPseudo)
        settingsFragment = SettingsFragment(userPseudo)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, discussionsFragment)
        transaction.commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }
    }

    private fun onNavigationItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.messages -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                transaction.replace(R.id.fragment, discussionsFragment)
                transaction.commit()
            }
            R.id.contacts -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                transaction.replace(R.id.fragment, contactsFragment)
                transaction.commit()
            }
            R.id.settings -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                transaction.replace(R.id.fragment, settingsFragment)
                transaction.commit()
            }
        }
        return true
    }

    fun buttonCancelOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        findViewById<TextView>(R.id.textInput_pseudo)?.text = userPseudo
    }

    fun buttonValidateOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        if (findViewById<TextView>(R.id.textInput_pseudo)?.text!!.isNotEmpty()) {
            api.setPseudo({
                if (it.error != null) {
                    findViewById<TextView>(R.id.textInput_pseudo)?.text = userPseudo
                } else {
                    userPseudo = findViewById<TextView>(R.id.textInput_pseudo)?.text.toString()
                    discussionsFragment = DiscussionsFragment(userId, userPseudo)
                    contactsFragment = ContactsFragment(userId, userPseudo)
                    settingsFragment = SettingsFragment(userPseudo)
                }
            }, {
                findViewById<TextView>(R.id.textInput_pseudo)?.text = userPseudo
            }, findViewById<TextView>(R.id.textInput_pseudo)?.text.toString())
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
