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
import com.example.projetmobile4a.model.RestGroupOrUser
import com.example.projetmobile4a.model.RestGroupsList
import com.example.projetmobile4a.model.RestUsersList
import kotlinx.android.synthetic.main.fragment_messages.*


class MessagesFragment(private var userId: Int = 0, private var userPseudo: String = "") : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: UsersListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var api: Rest = Rest.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = UsersListAdapter(ArrayList(), ArrayList(), this::updateUserList, userId, userPseudo)
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
