package com.example.testwordkot.domain.repository

import com.example.testwordkot.domain.model.UserDomain

interface LoginRepository{

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}