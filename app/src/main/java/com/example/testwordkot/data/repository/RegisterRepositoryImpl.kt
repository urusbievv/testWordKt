package com.example.testwordkot.data.repository

import com.example.testwordkot.data.model.User
import com.example.testwordkot.data.storage.UserRegisterStorage
import com.example.testwordkot.domain.model.UserDomain
import com.example.testwordkot.domain.repository.RegisterRepository


class RegisterRepositoryImpl(private val userRegisterStorage: UserRegisterStorage):
    RegisterRepository {

    override fun registerUser(user: UserDomain, callback: (Boolean) -> Unit) =
        userRegisterStorage.register(mapToStorage(user)) { success ->
            callback(success)
        }


    private fun mapToStorage(userDomain: UserDomain): User =
        User(name = userDomain.name, email = userDomain.email, password = userDomain.password,
            phone = userDomain.phone, schoolClass = userDomain.schoolClass)

}