package com.example.projetmobile4a


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestUser
import com.example.projetmobile4a.model.RestUsersList
import kotlinx.android.synthetic.main.fragment_messages.*


class MessagesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var users: List<RestUser> = ArrayList()
    private var friends: List<RestUser> = ArrayList()

    private var userId = 0
    private var userPseudo = ""

    private var api: Rest = Rest.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userId = arguments?.getInt("USER_ID") ?: 0
        userPseudo = arguments?.getString("USER_PSEUDO") ?: ""

        viewManager = LinearLayoutManager(activity)
        viewAdapter = UsersListAdapter(users, friends, this::updateUserList, userId)
        recyclerView = my_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val dividerItemDecoration = DividerItemDecoration(my_recycler_view.context,  (viewManager as LinearLayoutManager).orientation)
        my_recycler_view.addItemDecoration(dividerItemDecoration)

        updateUserList()
    }

    private fun updateUserList() {
        api.getDiscussions(fun (users: RestUsersList) {
            if (users.error == null) {
                api.getFriends(fun(friends: RestUsersList) {
                    if (friends.error == null) {
                        this.users = users.users!!
                        this.friends = friends.users!!
                        recyclerView.swapAdapter( UsersListAdapter(this.users, this.friends, this::updateUserList, userId), true)
                    }
                }, null)
            }
        }, null)
    }
}
