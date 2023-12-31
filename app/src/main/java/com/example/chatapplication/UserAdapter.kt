package com.example.chatapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context

class UserAdapter(val context: android.content.Context, val userlist: ArrayList<User>):
    RecyclerView.Adapter <UserAdapter.UserViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return UserViewHolder(view)

    }

    override fun getItemCount(): Int {
        return  userlist.size

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
      val currentUser = userlist[position]
        holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context, chatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid", currentUser?.uid)
            context.startActivity(intent)
        }
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.user)

    }
}