package com.example.testwordkot.data.storage.repository

import com.example.testwordkot.data.storage.UserLoginStorage
import com.google.firebase.auth.FirebaseAuth


class LoginFirebaseStorage : UserLoginStorage {

    private val authStorage = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authStorage.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure("Ошибка авторизации")
            }
    }
}