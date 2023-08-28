package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView :RecyclerView
    private lateinit var messageBox : TextView
    private lateinit var  sendbtn : ImageView
    private lateinit var  messageAdapter: messageAdapter
    private lateinit var messageList:ArrayList<com.example.chatapplication.Message>
    private lateinit var  mDB :DatabaseReference



    var receiverRoom: String? = null
    var senderRoom: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name = intent.getStringExtra("name")
        val ReceiverUid = intent.getStringExtra("uid")
        val SenderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDB = FirebaseDatabase.getInstance().getReference()

        senderRoom = ReceiverUid + SenderUid
        receiverRoom = SenderUid + ReceiverUid

        supportActionBar?.title = name


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.msg_box)
        sendbtn = findViewById(R.id.send_btn)
        messageList = ArrayList()
        messageAdapter = messageAdapter(this,messageList)


        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter





        mDB.child("Chats").child(senderRoom!!).child("Messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(com.example.chatapplication.Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })









        sendbtn.setOnClickListener{
            //adding the message to database
            val message = messageBox.text.toString()
            val messageObject = com.example.chatapplication.Message(message, SenderUid )
            mDB.child("Chats").child(senderRoom!!).child("Messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDB.child("Chats").child(receiverRoom!!).child("Messages").push()
                        .setValue(messageObject)

                }
            messageBox.setText("")

        }



    }
}