package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class messageAdapter(val context: Context, val messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType ==1){
            //inflate receive
           val view:View = LayoutInflater.from(context).inflate(R.layout.receive, parent,false)
            return  recieveViewholder(view)
        }else{
            //inflate sent
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent, parent,false)
            return  sentViewholder(view)

        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass==sentViewholder::class.java){
            val currentMessage = messageList[position]

            val viewholder = holder as sentViewholder
            holder.sentMessage.text = currentMessage.message

        }else{
            val currentMessage = messageList[position]


            val viewholder = holder as recieveViewholder
            holder.recieveMessage.text = currentMessage.message

        }

    }
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return  ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }
    override fun getItemCount(): Int {
        return messageList.size


    }




    class sentViewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val  sentMessage = itemView.findViewById<TextView>(R.id.sent_message)

    }
    class recieveViewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val recieveMessage = itemView.findViewById<TextView>(R.id.receive_message)

    }







}