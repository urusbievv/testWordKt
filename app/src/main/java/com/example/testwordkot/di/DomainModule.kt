package com.example.testwordkot.di

import com.example.testwordkot.data.repository.LoginRepositoryImpl
import com.example.testwordkot.data.storage.UserLoginStorage
import com.example.testwordkot.data.storage.repository.LoginFirebaseStorage
import com.example.testwordkot.domain.repository.LoginRepository
import com.example.testwordkot.domain.usecase.UserLoginUseCase
import com.example.testwordkot.domain.usecase.UserRegisterUseCase
import org.koin.dsl.module


val domainModule = module {

    factory{ UserLoginUseCase(loginRepository = get()) }
    factory{ UserRegisterUseCase(registerRepository = get()) }
}