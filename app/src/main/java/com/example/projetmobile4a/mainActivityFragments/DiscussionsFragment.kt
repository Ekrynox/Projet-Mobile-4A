package com.example.projetmobile4a.mainActivityFragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.R
import com.example.projetmobile4a.UsersListAdapter
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestGroupOrUser
import com.example.projetmobile4a.model.RestGroupsList
import com.example.projetmobile4a.model.RestUsersList


class DiscussionsFragment(private var userId: Int, private var userPseudo: String) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: UsersListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var api: Rest = Rest.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discussions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = UsersListAdapter(
            ArrayList(),
            ArrayList(),
            this::updateUserList,
            userId,
            userPseudo
        )
        recyclerView = activity!!.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,  (viewManager as LinearLayoutManager).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        updateUserList()

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                updateUserList()
                handler.postDelayed(this, 10000)
            }
        }
        handler.postDelayed(runnable, 10000)
    }

    private fun updateUserList() {
        api.getDiscussions(fun (users: RestUsersList) {
            if (users.error == null) {
                api.getFriends(fun(friends: RestUsersList) {
                    if (friends.error == null) {
                        api.getGroups(fun (groups: RestGroupsList) {
                            if (groups.error == null) {
                                val data = ArrayList<RestGroupOrUser>()
                                for (user in users.users!!) {
                                    data.add(RestGroupOrUser(user))
                                }
                                for (group in groups.groups!!) {
                                    data.add(RestGroupOrUser(group))
                                }
                                viewAdapter.updateDataset(data, friends.users!!)
                            }
                        }, null)
                    }
                }, null)
            }
        }, null)
    }
}
