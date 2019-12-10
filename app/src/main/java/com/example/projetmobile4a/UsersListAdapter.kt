package com.example.projetmobile4a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestDefault
import com.example.projetmobile4a.model.RestUser
import com.google.android.material.button.MaterialButton

class UsersListAdapter(private val users: List<RestUser>, private val friends: List<RestUser>, private val update: (() -> Unit)?, private val userId: Int) :
    RecyclerView.Adapter<UsersListAdapter.MyViewHolder>() {

    private var rest: Rest = Rest.getInstance()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.textView).text = users[position].pseudo

        if (users[position].id == userId) {
            return
        }

        var ok = false
        for (friend in friends) {
            if (friend.id == users[position].id) {
                ok = true
            }
        }

        val button = holder.view.findViewById<MaterialButton>(R.id.add_remove_button)
        val context = holder.view.context

        if (ok) {
            button.text = context.getString(R.string.remove)
            button.icon = context.getDrawable(R.drawable.ic_person_outline_black_24dp)
            button.iconTint = context.getColorStateList(R.color.colorRemove)
            button.setTextColor(context.getColorStateList(R.color.colorRemove))
            button.rippleColor = context.getColorStateList(R.color.colorRemove)

            button.setOnClickListener {
                rest.removeFriend(fun (res: RestDefault) {
                    if (res.error == null) {
                        update?.invoke()
                    }
                }, null, users[position].id!!)
            }
        } else {
            button.text = context.getString(R.string.add)
            button.icon = context.getDrawable(R.drawable.ic_person_add_black_24dp)
            button.iconTint = context.getColorStateList(R.color.colorAdd)
            button.setTextColor(context.getColorStateList(R.color.colorAdd))
            button.rippleColor = context.getColorStateList(R.color.colorAdd)

            button.setOnClickListener {
                rest.addFriend(fun (res: RestDefault) {
                    if (res.error == null) {
                        update?.invoke()
                    }
                }, null, users[position].id!!)
            }
        }
    }

    override fun getItemCount() = users.size
}