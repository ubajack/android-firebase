package com.ubalia.firebase.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        auth = Firebase.auth

        Toast.makeText(
            baseContext,
            "Hello, ${auth.currentUser?.email}",
            Toast.LENGTH_SHORT
        ).show()

        findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}