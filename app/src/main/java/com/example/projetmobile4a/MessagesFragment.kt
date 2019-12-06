package com.example.projetmobile4a


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestUser
import com.example.projetmobile4a.model.RestUsersList
import kotlinx.android.synthetic.main.fragment_messages.*
import java.util.*


class MessagesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var rest : Rest? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rest = Rest.getInstance()
        rest?.getDiscussions(this::updateUserList, null)

        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    private fun updateUserList(users: RestUsersList) {
        if (users.error == null) {
            viewManager = LinearLayoutManager(activity)
            viewAdapter = UsersListAdapter(users.users!!)
            recyclerView = my_recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }

            return
        }

        Log.d(this.toString(), users.error!!)
    }
}
