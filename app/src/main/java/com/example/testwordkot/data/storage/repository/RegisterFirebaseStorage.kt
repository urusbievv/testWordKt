package com.example.testwordkot.data.storage.repository

import com.example.testwordkot.data.model.User
import com.example.testwordkot.data.storage.UserRegisterStorage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterFirebaseStorage : UserRegisterStorage {

    private val authStorage = FirebaseAuth.getInstance()
    private val databaseStorage = FirebaseDatabase.getInstance().getReference("Users")

    override fun register(user: User, callback: (Boolean) -> Unit) {
        authStorage.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = authStorage.currentUser?.uid

                    userId?.let {
                        // Сохраняем данные пользователя в базе данных
                        databaseStorage.child(it).setValue(user)
                            .addOnSuccessListener {
                                // Данные успешно сохранены
                                callback(true) // Регистрация успешна
                            }
                            .addOnFailureListener { e ->
                                // Обработка ошибки при сохранении данных
                                callback(false)
                            }
                    }
                } else {
                    // Обработка ошибки при создании пользователя в Firebase Authentication
                    callback(false) // Ошибка при создании пользователя
                }
            }
    }

}