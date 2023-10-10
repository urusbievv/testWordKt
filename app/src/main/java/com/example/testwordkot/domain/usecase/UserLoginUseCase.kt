package com.example.testwordkot.domain.usecase

import com.example.testwordkot.domain.repository.LoginRepository

class UserLoginUseCase(private val loginRepository: LoginRepository) {

  fun execute(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) =
    loginRepository.loginUser(email,password, onSuccess, onFailure)

}