package com.example.testwordkot.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.testwordkot.R
import com.example.testwordkot.presentation.viewModel.MainViewModel
import com.example.testwordkot.presentation.viewModel.factory.MainViewModelFactory

import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnAdmin: Button
    private lateinit var root: RelativeLayout
    private lateinit var avtores: LinearLayout
    private lateinit var inputLayout: LinearLayout
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText


    private val vm: MainViewModel by viewModels() { MainViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initViews()
        vm.errorLiveData.observe(this, Observer { errorMessage ->
            showSnackBar(errorMessage)
        })
        vm.successLiveData.observe(this, Observer { success ->
            if (success) {
                val intent = Intent(this@MainActivity, LevelActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        vm.adminPassword.observe(this, Observer { isAdminPassword ->
            if (isAdminPassword) {
                val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
                startActivity(intent)
            } else {
                showSnackBar("Неверный пароль")
            }
        })

        btnAdmin.setOnClickListener { showAdminPasswordDialog() }
        btnLogin.setOnClickListener { onLoginButtonClicked() }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initViews() {
        root = findViewById(R.id.root_element)
        avtores = findViewById(R.id.avtores)
        inputLayout = findViewById(R.id.inputLayout)

        btnLogin = findViewById(R.id.btn_login)
        btnAdmin = findViewById(R.id.btn_admin)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
    }

    private fun showAdminPasswordDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        builder.setView(dialogView)

        val passwordEditText = dialogView.findViewById<EditText>(R.id.passwordEditText)
        builder.setPositiveButton("OK") { _, _ ->
            val enteredPassword = passwordEditText.text.toString()
            vm.checkAdminPassword(enteredPassword)
        }

        builder.setNegativeButton("Отмена", null)

        builder.show()
    }

    private fun onLoginButtonClicked() {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()
        vm.onLoginClicked(email, password)
    }


}
