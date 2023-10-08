package com.example.testwordkot.presentation.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.example.testwordkot.data.repository.RegisterRepositoryImpl
import com.example.testwordkot.data.storage.repository.FirebaseStorageRegister
import com.example.testwordkot.domain.model.UserDomain
import com.example.testwordkot.domain.usecase.UserRegisterUseCase
import com.google.firebase.auth.FirebaseAuth


class UserRegistrationActivity : AppCompatActivity() {

    private val userRegisterStorage by lazy { FirebaseStorageRegister() }
    private val userRegisterRepository by lazy { RegisterRepositoryImpl(userRegisterStorage) }
    private val userRegisterUseCase by lazy { UserRegisterUseCase(userRegisterRepository) }


    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var classEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var btnOpenStorage: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        auth = FirebaseAuth.getInstance()

        initViews()

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val schoolClass = classEditText.text.toString()

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                showToast("Введите все данные для регистрации")
            } else {
                val user = UserDomain(name, email, password, phone, schoolClass)


                userRegisterUseCase.execute(user) { success ->
                    runOnUiThread {
                        if (success) {
                            showToast("Регистрация успешна")
                            finish()
                        } else {
                            showToast("Ошибка при регистрации")
                        }
                    }
                }
            }
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        nameEditText = findViewById(R.id.registration_name)
        emailEditText = findViewById(R.id.registration_email)
        passwordEditText = findViewById(R.id.registration_password)
        phoneEditText = findViewById(R.id.registration_phone)
        classEditText = findViewById(R.id.registration_class)
        registerButton = findViewById(R.id.registration_register_button)
        btnOpenStorage = findViewById(R.id.btnOpenStorage)
    }


}