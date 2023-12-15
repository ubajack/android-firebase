package com.ubalia.firebase.authentication

import android.net.Uri

data class User(
    val email: String,
    // val password: String,
    val name: String? = null,
    val photoUrl: Uri? = null,
    val emailVerified: Boolean? = null,
    val uid: String? = null
)