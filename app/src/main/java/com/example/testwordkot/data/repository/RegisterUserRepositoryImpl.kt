package com.example.testwordkot.data.repository

import com.google.firebase.auth.FirebaseAuth



class RegisterUserRepositoryImpl : RegisterUserRepository {
    private lateinit var auth: FirebaseAuth


    override fun registerUser(name: String, email: String, password: String, phone: String, classE: String) {
        auth = FirebaseAuth.getInstance()

    }
}
