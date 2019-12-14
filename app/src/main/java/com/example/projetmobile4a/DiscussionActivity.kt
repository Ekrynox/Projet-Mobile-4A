package com.example.projetmobile4a

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestMessageList
import com.example.projetmobile4a.model.RestUser
import kotlinx.android.synthetic.main.fragment_messages.*


class DiscussionActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
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

        api.getMessages(fun (messages: RestMessageList) {
            if (messages.error == null) {
                api.getUserById(fun(user: RestUser) {
                    if (user.error == null) {
                        viewManager = LinearLayoutManager(this)
                        viewAdapter = MessagesListAdapter(
                            messages.messages!!,
                            listOf(user),
                            null,
                            userId,
                            userPseudo
                        )
                        recyclerView = my_recycler_view.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }

                        val dividerItemDecoration = DividerItemDecoration(
                            my_recycler_view.context,
                            (viewManager as LinearLayoutManager).orientation
                        )
                        my_recycler_view.addItemDecoration(dividerItemDecoration)

                        return
                    }
                }, null, discussionId)
            }
        }, null, discussionId)
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
}
