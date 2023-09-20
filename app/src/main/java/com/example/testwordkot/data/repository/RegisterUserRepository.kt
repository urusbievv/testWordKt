package com.example.testwordkot.data.repository


interface RegisterUserRepository {
    fun registerUser(name: String, email: String, password: String, phone: String, classE: String)
}