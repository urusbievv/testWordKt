package com.example.testwordkot.presentation.viewModel.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testwordkot.data.repository.LoginRepositoryImpl
import com.example.testwordkot.data.storage.repository.LoginFirebaseStorage
import com.example.testwordkot.domain.usecase.UserLoginUseCase
import com.example.testwordkot.presentation.viewModel.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {

    private val userLoginStorage by lazy { LoginFirebaseStorage() }
    private val userLoginRepository by lazy { LoginRepositoryImpl(userLoginStorage) }
    private val userLoginUseCase by lazy { UserLoginUseCase(userLoginRepository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            userLoginUseCase = userLoginUseCase
        ) as T
    }
}