package com.example.projetmobile4a

import android.text.Html
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

class MessagesListAdapter(private var messages: List<RestMessage>, private var users: List<RestUser>, private val userId: Int, private val userPseudo: String) :
    RecyclerView.Adapter<MessagesListAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun updateDataset(messages: List<RestMessage>, users: List<RestUser>) {
        this.messages = messages
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messages_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.view.context

        val chip = holder.view.findViewById<TextView>(R.id.chip)
        chip.text = Html.fromHtml(messages[position].data?.text , Html.FROM_HTML_MODE_LEGACY)

        if (messages[position].user == userId) {
            chip.background.setTintList(context.getColorStateList(R.color.colorAccent))
            chip.setTextColor(context.getColorStateList(R.color.colorPrimary))
            holder.view.findViewById<LinearLayout>(R.id.linear_layout).gravity = Gravity.END
            holder.view.findViewById<TextView>(R.id.pseudo).text = Html.fromHtml(userPseudo , Html.FROM_HTML_MODE_LEGACY)
        } else {
            chip.background.setTintList(context.getColorStateList(R.color.colorPrimaryDark))
            chip.setTextColor(context.getColorStateList(R.color.black))
            holder.view.findViewById<LinearLayout>(R.id.linear_layout).gravity = Gravity.START
            holder.view.findViewById<TextView>(R.id.pseudo).text = Html.fromHtml(users.find { it.id == messages[position].user }?.pseudo , Html.FROM_HTML_MODE_LEGACY)
        }

        if (position + 1 < itemCount && messages[position + 1].user == messages[position].user) {
            holder.view.findViewById<TextView>(R.id.pseudo).visibility = View.GONE
        }
    }

    override fun getItemCount() = messages.size
}