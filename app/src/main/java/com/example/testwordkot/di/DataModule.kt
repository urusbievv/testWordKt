package com.example.testwordkot.di

import com.example.testwordkot.data.repository.LoginRepositoryImpl
import com.example.testwordkot.data.repository.RegisterRepositoryImpl
import com.example.testwordkot.data.storage.UserLoginStorage
import com.example.testwordkot.data.storage.UserRegisterStorage
import com.example.testwordkot.data.storage.repository.LoginFirebaseStorage
import com.example.testwordkot.data.storage.repository.RegisterFirebaseStorage
import com.example.testwordkot.domain.repository.LoginRepository
import com.example.testwordkot.domain.repository.RegisterRepository
import org.koin.dsl.module

val dataModule = module {


    single<UserLoginStorage> { LoginFirebaseStorage() }
    single<LoginRepository>{ LoginRepositoryImpl(userLoginStorage = get()) }
    single<UserRegisterStorage> { RegisterFirebaseStorage() }
    single<RegisterRepository> { RegisterRepositoryImpl(userRegisterStorage = get()) }

}