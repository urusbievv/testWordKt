package com.example.testwordkot.data.storage

import com.example.testwordkot.data.model.User

interface UserLoginStorage {

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}