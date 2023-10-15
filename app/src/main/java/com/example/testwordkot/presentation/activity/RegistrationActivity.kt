package com.example.testwordkot.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.testwordkot.R
import com.example.testwordkot.presentation.viewModel.RegisterViewModel
import com.example.testwordkot.presentation.viewModel.factory.RegisterViewModelFactory
import com.google.android.material.snackbar.Snackbar




class RegistrationActivity : AppCompatActivity() {

    private val vm: RegisterViewModel by viewModels() { RegisterViewModelFactory() }
    private lateinit var root: RelativeLayout
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var classEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var btnOpenStorage: Button
    private lateinit var btnImageBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        initViews()
        vm.errorLiveData.observe(this, Observer { errorMessage ->
            showSnackBar(errorMessage)
        })
        vm.registrationLiveData.observe(this, Observer { success ->
            if (success) {
                showSnackBar("Регистрация успешна")
                finish()
            } else {
                showSnackBar("Ошибка при регистрации")
            }
        })
        registerButton.setOnClickListener {
           saveUserRegistration()
        }
        setButtonClickListeners()
    }
    private fun  saveUserRegistration(){
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val phone = phoneEditText.text.toString()
        val schoolClass = classEditText.text.toString()
        vm.saveUserRegistration(name, email, password, phone, schoolClass)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initViews() {
        root = findViewById(R.id.root_element)
        nameEditText = findViewById(R.id.registration_name)
        emailEditText = findViewById(R.id.registration_email)
        passwordEditText = findViewById(R.id.registration_password)
        phoneEditText = findViewById(R.id.registration_phone)
        classEditText = findViewById(R.id.registration_class)
        registerButton = findViewById(R.id.registration_register_button)
        btnOpenStorage = findViewById(R.id.btnOpenStorage)
        btnImageBack = findViewById(R.id.imageButton4)
    }

    private fun setButtonClickListeners() {
        btnImageBack.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}