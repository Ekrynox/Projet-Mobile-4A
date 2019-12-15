package com.example.projetmobile4a

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.*
import com.google.android.material.textfield.TextInputEditText


class MessagesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessagesListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var userId = 0
    private var userPseudo = ""
    private var discussionId = 0
    private var discussionType = 0

    private var api: Rest = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)


        userId = intent.extras?.getInt("USER_ID") ?: 0
        userPseudo = intent.extras?.getString("USER_PSEUDO") ?: ""
        discussionId = intent.extras?.getInt("USER2_ID") ?: 0
        if (discussionId == 0) {
            discussionId = intent.extras?.getInt("GROUP_ID") ?: 0
            if (discussionId != 0) {
                discussionType = 2
            }
        } else {
            discussionType = 1
        }


        viewManager = LinearLayoutManager(this)
        viewAdapter = MessagesListAdapter(ArrayList(), ArrayList(), userId, userPseudo)
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
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
        if (discussionType == 1) {
            api.getMessages(fun(messages: RestMessageList) {
                if (messages.error == null) {
                    api.getUserById(fun(user: RestUser) {
                        if (user.error == null) {
                            viewAdapter.updateDataset(messages.messages!!, listOf(user))
                            scroll && findViewById<NestedScrollView>(R.id.nestedScrollView).fullScroll(View.FOCUS_DOWN)
                        }
                    }, null, discussionId)
                }
            }, null, discussionId)
        } else if (discussionType == 2) {
            api.getMessagesGroups(fun(messages: RestMessageList) {
                if (messages.error == null) {
                    api.getGroupById(fun(group: RestGroup) {
                        if (group.error == null) {
                            viewAdapter.updateDataset(messages.messages!!, group.users!!)
                            scroll && findViewById<NestedScrollView>(R.id.nestedScrollView).fullScroll(View.FOCUS_DOWN)
                        }
                    }, null, discussionId)
                }
            }, null, discussionId)
        }
    }

    fun buttonSendOnClick(@Suppress("UNUSED_PARAMETER") view: View) {
        if (findViewById<TextInputEditText>(R.id.messageToSend).text == null || findViewById<TextInputEditText>(R.id.messageToSend).text!!.isEmpty()) {
            return
        }
        val data = RestMessageData()
        data.text = findViewById<TextInputEditText>(R.id.messageToSend).text.toString()
        if (discussionType == 1) {
            api.addMessages(fun(res: RestDefault) {
                if (res.error == null) {
                    findViewById<TextInputEditText>(R.id.messageToSend).text?.clear()
                    updateMessagesList()
                }
            }, null, discussionId, data)
        } else if (discussionType == 2) {
            api.addMessagesGroups(fun(res: RestDefault) {
                if (res.error == null) {
                    findViewById<TextInputEditText>(R.id.messageToSend).text?.clear()
                    updateMessagesList()
                }
            }, null, discussionId, data)
        }
    }
}
