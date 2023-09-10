package com.example.testwordkot.activity.registration

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.example.testwordkot.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserRegistrationActivity : AppCompatActivity() {

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
            val classE = classEditText.text.toString()


            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                showToast("Введите все данные для регистрации")
            } else {
                registerUser(name, email, password, phone, classE)
            }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initViews(){
        nameEditText = findViewById(R.id.registration_name)
        emailEditText = findViewById(R.id.registration_email)
        passwordEditText = findViewById(R.id.registration_password)
        phoneEditText = findViewById(R.id.registration_phone)
        classEditText = findViewById(R.id.registration_class)
        registerButton = findViewById(R.id.registration_register_button)
        btnOpenStorage = findViewById(R.id.btnOpenStorage)
    }

    private fun registerUser(name: String, email: String, password: String, phone: String, classE: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid

                    // Создаем объект User
                    val newUser = User(name, email, password, phone, classE)

                    // Ссылка на базу данных
                    val databaseRef = FirebaseDatabase.getInstance().getReference("Users")

                    userId?.let {
                        databaseRef.child(it).setValue(newUser)
                            .addOnSuccessListener {
                                showToast("Регистрация успешна")
                                finish()
                            }
                            .addOnFailureListener { e ->
                                showToast("Ошибка регистрации: ${e.message}")
                            }
                    }
                } else {
                    showToast("Ошибка при регистрации: ${task.exception?.message}")
                }
            }
    }

//    private fun openFirebaseStorage() {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        currentUser?.let {
//            val userEmail = it.email
//            userEmail?.let { email ->
//                val sanitizedEmail = sanitizeEmail(email)
//
//                // Создаем URL для просмотра папки пользователя в Firebase Storage
//                val storageUrl = "должны быть ссылка$sanitizedEmail"
//
//                // Открываем URL в веб-браузере
//                val uri = Uri.parse(storageUrl)
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                startActivity(intent)
//            }
//        }
//    }

    private fun sanitizeEmail(email: String): String {
        return email.replace(".", "_").replace("@", "_")
    }


    fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    fun goBack(view: View) {}


}