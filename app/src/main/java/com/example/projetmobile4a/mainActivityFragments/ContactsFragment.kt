package com.example.projetmobile4a.mainActivityFragments


import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
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
import com.example.projetmobile4a.model.RestUsersList
import com.google.android.material.textfield.TextInputEditText


class ContactsFragment(private var userId: Int, private var userPseudo: String) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: UsersListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var api: Rest = Rest.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.findViewById<TextInputEditText>(R.id.textInput_filter)?.setImeActionLabel(getString(R.string.search), KeyEvent.KEYCODE_ENTER)
        activity?.findViewById<TextInputEditText>(R.id.textInput_filter)?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent
            ): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    updateUserList()
                    return true
                }
                return false
            }
        })

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
        if (activity?.findViewById<TextInputEditText>(R.id.textInput_filter)?.text?.isEmpty() != false) {
            api.getFriends(fun(users: RestUsersList) {
                if (users.error == null) {
                    api.getFriends(fun(friends: RestUsersList) {
                        if (friends.error == null) {
                            val data = ArrayList<RestGroupOrUser>()
                            for (user in users.users!!) {
                                data.add(RestGroupOrUser(user))
                            }
                            viewAdapter.updateData(data, friends.users!!)
                        }
                    }, null)
                }
            }, null)
        } else {
            api.searchUser(fun(users: RestUsersList) {
                if (users.error == null) {
                    api.getFriends(fun(friends: RestUsersList) {
                        if (friends.error == null) {
                            val data = ArrayList<RestGroupOrUser>()
                            for (user in users.users!!) {
                                data.add(RestGroupOrUser(user))
                            }
                            viewAdapter.updateData(data, friends.users!!)
                        }
                    }, null)
                }
            }, null, activity?.findViewById<TextInputEditText>(R.id.textInput_filter)?.text.toString())
        }
    }
}
