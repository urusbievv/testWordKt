package com.example.testwordkot.ui.activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnAdmin: Button
    private lateinit var root: RelativeLayout
    private lateinit var avtores: LinearLayout
    private lateinit var inputLayout: LinearLayout
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var auth: FirebaseAuth // вынести в слой дата и сделать абстракции в домене

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        initViews()
        btnAdmin.setOnClickListener { showAdminPasswordDialog() }
        btnLogin.setOnClickListener { onLoginButtonClicked() }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // тосты лучше не показывать, например, snackbar
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initViews(){
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
            val correctPassword = "123456" // Пароль для входа в режим Admin
            if (enteredPassword == correctPassword) {
                val intent = Intent(this@MainActivity, UserRegistrationActivity::class.java)
                startActivity(intent)
            } else {
                    showSnackBar("Неверный пароль") // все ресурсы лежат в xml и как const
            }
        }

        builder.setNegativeButton("Отмена", null)

        builder.show()
    }


    private fun onLoginButtonClicked(){
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            showSnackBar("Введите все данные для авторизации")
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(this@MainActivity, LevelActivity::class.java)
                    startActivity(intent) // все ресурсы лежат в xml и как const
                    finish()
                }
                .addOnFailureListener {
                    showSnackBar("Ошибка авторизации")
                }
        }
    }
}