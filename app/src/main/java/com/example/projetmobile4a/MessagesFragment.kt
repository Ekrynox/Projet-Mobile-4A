package com.example.projetmobile4a


import android.os.Bundle
import android.util.Log
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

    private var rest : Rest? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rest = Rest.getInstance()
        rest?.getDiscussions(fun (users: RestUsersList) {
            rest?.getFriends(fun (friends: RestUsersList) {
                this.updateUserList(users, friends)
            }, null)
        }, null)

        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    private fun updateUserList(users: RestUsersList, friends: RestUsersList) {
        if (users.error == null) {
            viewManager = LinearLayoutManager(activity)
            viewAdapter = UsersListAdapter(users.users!!, friends.users!!)
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

        Log.d(this.toString(), users.error!!)
    }
}
