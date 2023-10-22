package com.example.testwordkot.domain.repository

import com.example.testwordkot.domain.model.UserDomain


interface RegisterRepository {
    fun registerUser(user: UserDomain, callback: (Boolean) -> Unit)
}