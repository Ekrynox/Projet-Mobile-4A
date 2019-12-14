package com.example.projetmobile4a

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestDefault
import com.example.projetmobile4a.model.RestMessageData
import com.example.projetmobile4a.model.RestMessageList
import com.example.projetmobile4a.model.RestUser
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_discussion.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_messages.my_recycler_view


class DiscussionActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessagesListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var userId = 0
    private var userPseudo = ""
    private var discussionId = 0

    private var api: Rest = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)


        userId = intent.extras?.getInt("USER_ID") ?: 0
        userPseudo = intent.extras?.getString("USER_PSEUDO") ?: ""
        discussionId = intent.extras?.getInt("USER2_ID") ?: 0


        viewManager = LinearLayoutManager(this)
        viewAdapter = MessagesListAdapter(ArrayList(), ArrayList(), userId, userPseudo)
        recyclerView = my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        updateMessagesList(true)

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                updateMessagesList()
                handler.postDelayed(this, 5000)
            }
        }
        handler.postDelayed(runnable, 5000)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.back -> {
            finish()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateMessagesList(scroll: Boolean = false) {
        api.getMessages(fun (messages: RestMessageList) {
            if (messages.error == null) {
                api.getUserById(fun(user: RestUser) {
                    if (user.error == null) {
                        viewAdapter.updateDataset(messages.messages!!, listOf(user))
                        scroll && nestedScrollView.fullScroll(View.FOCUS_DOWN)
                    }
                }, null, discussionId)
            }
        }, null, discussionId)
    }

    fun buttonSendOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        if (findViewById<TextInputEditText>(R.id.messageToSend).text == null || findViewById<TextInputEditText>(R.id.messageToSend).text!!.isEmpty()) {
            return
        }
        val data = RestMessageData()
        data.text = findViewById<TextInputEditText>(R.id.messageToSend).text.toString()
        api.addMessages(fun (res: RestDefault) {
            if (res.error == null) {
                findViewById<TextInputEditText>(R.id.messageToSend).text?.clear()
                updateMessagesList()
            }
        }, null, discussionId, data)
    }
}
