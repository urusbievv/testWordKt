package com.example.testwordkot.presentation.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testwordkot.data.repository.RegisterRepositoryImpl
import com.example.testwordkot.data.storage.repository.RegisterFirebaseStorage
import com.example.testwordkot.domain.usecase.UserRegisterUseCase
import com.example.testwordkot.presentation.viewModel.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory : ViewModelProvider.Factory {

    private val userRegisterStorage by lazy { RegisterFirebaseStorage() }
    private val userRegisterRepository by lazy { RegisterRepositoryImpl(userRegisterStorage) }
    private val userRegisterUseCase by lazy { UserRegisterUseCase(userRegisterRepository) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(
            userRegisterUseCase = userRegisterUseCase
        ) as T
    }
}