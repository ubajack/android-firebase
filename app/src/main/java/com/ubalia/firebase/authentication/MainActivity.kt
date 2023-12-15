package com.ubalia.firebase.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Log.d(TAG, "Created user: ${getCurrentUser()}")
                    goToUserPage()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Log.d(TAG, "Created user: ${getCurrentUser()}")
                    goToUserPage()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signOut() {
        auth.signOut()
    }

    private fun getCredentials(): Pair<String, String> {
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val credentials = Pair(email.text.toString(), password.text.toString())

        email.text.clear()
        password.text.clear()

        return credentials
    }

    private fun goToUserPage() {
        Intent(this, UserActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth

        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            val credentials = getCredentials()
            createAccount(credentials.first, credentials.second)
        }

        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            val credentials = getCredentials()
            signIn(credentials.first, credentials.second)
        }
    }

    private fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        var user: User? = null
        currentUser?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl
            val emailVerified = it.isEmailVerified
            val uid = it.uid

            user = User(email!!, name, photoUrl, emailVerified, uid)
        }
        return user
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User connected
            Log.d(TAG, "Connected user: ${getCurrentUser()}")
            signOut()
        } else {
            // User not connected
            Log.w(TAG, "User not signed in")
            // signIn("jean.bon@email.com", "123pass")
        }
    }
}