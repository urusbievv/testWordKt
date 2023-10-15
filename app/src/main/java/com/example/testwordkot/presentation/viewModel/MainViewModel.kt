package com.example.testwordkot.presentation.viewModel


import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testwordkot.domain.usecase.UserLoginUseCase

class MainViewModel(private val userLoginUseCase: UserLoginUseCase): ViewModel() {

    private val _adminPassword = MutableLiveData<Boolean>()
    val adminPassword: LiveData<Boolean> get() = _adminPassword

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData
    private val _successLiveData = MutableLiveData<Boolean>()
    val successLiveData: LiveData<Boolean> get() = _successLiveData

    fun checkAdminPassword(enteredPassword: String) {
        val correctPassword = "123456" // Пароль для входа в режим Admin
        _adminPassword.value = enteredPassword == correctPassword
    }

    fun onLoginClicked(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            _errorLiveData.value = "Введите все данные для авторизации"
        } else {
            userLoginUseCase.execute(
                email,
                password,
                onSuccess = {
                    _successLiveData.value = true
                },
                onFailure = { errorMessage ->

                    _errorLiveData.value = errorMessage
                }
            )
        }
    }
}