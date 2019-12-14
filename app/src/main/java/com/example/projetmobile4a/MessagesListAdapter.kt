package com.example.projetmobile4a

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestMessage
import com.example.projetmobile4a.model.RestUser
import com.google.android.material.chip.Chip

class MessagesListAdapter(private val messages: List<RestMessage>, private val users: List<RestUser>, private val update: (() -> Unit)?, private val userId: Int, private val userPseudo: String) :
    RecyclerView.Adapter<MessagesListAdapter.MyViewHolder>() {

    private var api: Rest = Rest.getInstance()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.view.context

        val chip = holder.view.findViewById<Chip>(R.id.chip)
        chip.text = messages[position].data?.text

        if (messages[position].user == userId) {
            chip.chipBackgroundColor = context.getColorStateList(R.color.colorAccent)
            chip.setTextColor(context.getColorStateList(R.color.colorPrimary))
            holder.view.findViewById<LinearLayout>(R.id.linear_layout).gravity = Gravity.END
            holder.view.findViewById<TextView>(R.id.pseudo).text = userPseudo
        } else {
            holder.view.findViewById<TextView>(R.id.pseudo).text = users.find { it.id == messages[position].user }?.pseudo
        }

        if (position + 1 < itemCount && messages[position + 1].user == messages[position].user) {
            holder.view.findViewById<TextView>(R.id.pseudo).visibility = View.GONE
        }
    }

    override fun getItemCount() = messages.size
}