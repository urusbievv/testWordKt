package com.example.testwordkot.data.storage

import com.example.testwordkot.data.model.User

interface UserRegisterStorage {

    fun register(user: User, callback: (Boolean) -> Unit)

}