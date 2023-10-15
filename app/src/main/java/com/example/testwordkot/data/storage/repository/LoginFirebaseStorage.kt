package com.example.testwordkot.data.storage.repository

import com.example.testwordkot.data.storage.UserLoginStorage
import com.example.testwordkot.data.storage.utils.ConstantsDATA.LOGIN_ERROR_MESSAGE
import com.google.firebase.auth.FirebaseAuth



class LoginFirebaseStorage : UserLoginStorage {

    private val authStorage = FirebaseAuth.getInstance()

    /**
     * @param onSuccess вызываем при успешном входе.
     * @param onFailure вызываем при неудачном входе.
     */
    override fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authStorage.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(LOGIN_ERROR_MESSAGE)
            }
    }
}