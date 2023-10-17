package com.example.testwordkot.di

import com.example.testwordkot.presentation.viewModel.MainViewModel
import com.example.testwordkot.presentation.viewModel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    viewModel{
        MainViewModel(userLoginUseCase = get())

    }
    viewModel {
        RegisterViewModel(userRegisterUseCase = get())
    }
}