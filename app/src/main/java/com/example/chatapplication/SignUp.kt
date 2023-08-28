package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var  edtName :EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignUp:Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var  mDB : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()


        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.name)


        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)

        btnSignUp = findViewById(R.id.signup_btn)

        btnSignUp.setOnClickListener {
           val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name =edtName.text.toString()


            signup(name, email, password)
        }
    }
    private fun signup(name: String,email:String, password:String){
        //logic for sign up
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDataBase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT ).show()


                }
        }



    }
    private fun addUserToDataBase(name: String, email: String,uid:String){
        mDB = FirebaseDatabase.getInstance().getReference()
        mDB.child("User").child(uid).setValue(User(name,email,uid))

    }
}