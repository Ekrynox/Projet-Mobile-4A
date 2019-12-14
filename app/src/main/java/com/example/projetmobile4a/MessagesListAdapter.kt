package com.example.projetmobile4a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestMessage
import com.example.projetmobile4a.model.RestUser

class MessagesListAdapter(private val messages: List<RestMessage>, private val users: List<RestUser>, private val update: (() -> Unit)?, private val userId: Int, private val userPseudo: String) :
    RecyclerView.Adapter<MessagesListAdapter.MyViewHolder>() {

    private var rest: Rest = Rest.getInstance()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //val context = holder.view.context
    }

    override fun getItemCount() = messages.size
}