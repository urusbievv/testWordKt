package com.example.testwordkot.data.repository


import com.example.testwordkot.data.storage.UserLoginStorage
import com.example.testwordkot.domain.repository.LoginRepository

class LoginRepositoryImpl(private val userLoginStorage: UserLoginStorage):
    LoginRepository {

    override fun loginUser(email: String, password: String,
                           onSuccess: () -> Unit, onFailure: (String) -> Unit) =
        userLoginStorage.login(email,password,onSuccess,onFailure)


}