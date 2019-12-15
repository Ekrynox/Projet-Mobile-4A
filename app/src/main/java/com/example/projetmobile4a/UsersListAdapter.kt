package com.example.projetmobile4a

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetmobile4a.controller.Rest
import com.example.projetmobile4a.model.RestDefault
import com.example.projetmobile4a.model.RestGroupOrUser
import com.example.projetmobile4a.model.RestUser
import com.google.android.material.button.MaterialButton



class UsersListAdapter(private var data: List<RestGroupOrUser>, private var friends: List<RestUser>, private val update: (() -> Unit)?, private val userId: Int, private val userPseudo: String) :
    RecyclerView.Adapter<UsersListAdapter.MyViewHolder>() {

    private var rest: Rest = Rest.getInstance()

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun updateDataset(data: List<RestGroupOrUser>, friends: List<RestUser>) {
        this.data = data
        this.friends = friends
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (data[position].user != null) {
            holder.view.findViewById<TextView>(R.id.textView).text = Html.fromHtml(data[position].user!!.pseudo , Html.FROM_HTML_MODE_LEGACY)

            val context = holder.view.context

            holder.view.findViewById<LinearLayout>(R.id.linear_layout).setOnClickListener {
                val intent = Intent(context, DiscussionActivity::class.java).apply {}
                intent.putExtra("USER_ID", userId)
                intent.putExtra("USER_PSEUDO", userPseudo)
                intent.putExtra("USER2_ID", data[position].user!!.id)
                context.startActivity(intent)
            }

            if (data[position].user!!.id == userId) {
                return
            }

            val button = holder.view.findViewById<MaterialButton>(R.id.add_remove_button)

            if (friends.find { it.id == data[position].user!!.id } != null) {
                button.text = context.getString(R.string.remove)
                button.icon = context.getDrawable(R.drawable.ic_person_outline_black_24dp)
                button.iconTint = context.getColorStateList(R.color.colorRemove)
                button.setTextColor(context.getColorStateList(R.color.colorRemove))
                button.rippleColor = context.getColorStateList(R.color.colorRemove)

                button.setOnClickListener {
                    rest.removeFriend(fun(res: RestDefault) {
                        if (res.error == null) {
                            update?.invoke()
                        }
                    }, null, data[position].user!!.id!!)
                }
            } else {
                button.text = context.getString(R.string.add)
                button.icon = context.getDrawable(R.drawable.ic_person_add_black_24dp)
                button.iconTint = context.getColorStateList(R.color.colorAdd)
                button.setTextColor(context.getColorStateList(R.color.colorAdd))
                button.rippleColor = context.getColorStateList(R.color.colorAdd)

                button.setOnClickListener {
                    rest.addFriend(fun(res: RestDefault) {
                        if (res.error == null) {
                            update?.invoke()
                        }
                    }, null, data[position].user!!.id!!)
                }
            }
        } else if(data[position].group != null) {
            holder.view.findViewById<TextView>(R.id.textView).text = Html.fromHtml(data[position].group!!.name , Html.FROM_HTML_MODE_LEGACY)

            val context = holder.view.context

            holder.view.findViewById<LinearLayout>(R.id.linear_layout).setOnClickListener {
                val intent = Intent(context, DiscussionActivity::class.java).apply {}
                intent.putExtra("USER_ID", userId)
                intent.putExtra("USER_PSEUDO", userPseudo)
                intent.putExtra("GROUP_ID", data[position].group!!.id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = data.size
}