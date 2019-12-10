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
import com.example.projetmobile4a.model.RestUsersList
import kotlinx.android.synthetic.main.fragment_messages.*


class MessagesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var userId = 0
    private var userPseudo = ""

    private var api: Rest = Rest.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userId = arguments?.getInt("USER_ID") ?: 0
        userPseudo = arguments?.getString("USER_PSEUDO") ?: ""

        api.getDiscussions(fun (users: RestUsersList) {
            api.getFriends(fun (friends: RestUsersList) {
                if (users.error == null) {
                    viewManager = LinearLayoutManager(activity)
                    viewAdapter = UsersListAdapter(users.users!!, friends.users!!, this::updateUserList, userId)
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
            }, null)
        }, null)

        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    private fun updateUserList() {
        api.getDiscussions(fun (users: RestUsersList) {
            api.getFriends(fun (friends: RestUsersList) {
                if (users.error == null) {
                    viewAdapter = UsersListAdapter(users.users!!, friends.users!!, this::updateUserList, userId)
                    recyclerView.swapAdapter(viewAdapter, false)
                    return
                }
            }, null)
        }, null)
    }
}
