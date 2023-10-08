package com.example.testwordkot.domain.usecase

import com.example.testwordkot.domain.model.UserDomain
import com.example.testwordkot.domain.repository.RegisterRepository


class UserRegisterUseCase(private val registerRepository: RegisterRepository){

    fun execute(user: UserDomain, callback: (Boolean) -> Unit) {
        registerRepository.registerUser(user) { success ->
            callback(success)
        }
    }

}