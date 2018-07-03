package ru.aleksandrtrushchinskii.surfproject.common.service

import com.google.firebase.auth.FirebaseAuth


class Authentication(private val firebaseAuth: FirebaseAuth) {

    val isAuth: Boolean
        get() = firebaseAuth.currentUser != null

    val uid: String
        get() = firebaseAuth.currentUser!!.uid

}