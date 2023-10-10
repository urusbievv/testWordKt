package com.example.testwordkot.data.storage.repository


import com.example.testwordkot.data.storage.AnswerStorage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth

class AnswerFirebaseStorage() : AnswerStorage {

    private fun sanitizeEmail(email: String): String {
        return email.replace(".", "_").replace("@", "_")
    }
    override suspend fun save(
        userEmail: String,
        newData: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            val sanitizedEmail = sanitizeEmail(userEmail)
            val fileName = "Блок_1_$sanitizedEmail.txt"
            val storageRef = FirebaseStorage.getInstance().reference
            val userRef = storageRef.child("users").child(sanitizedEmail)
            val answersRef = userRef.child("Блок_1").child(fileName)

            val existingDataBytes = try {
                answersRef.getBytes(Long.MAX_VALUE).await()
            } catch (e: Exception) {
                byteArrayOf()
            }

            val existingData = String(existingDataBytes, Charsets.UTF_8)
            val updatedData = if (existingData.isNotEmpty()) {
                "$existingData\n----------------\n$newData"
            } else {
                newData
            }

            val updatedDataBytes = updatedData.toByteArray()

            answersRef.putBytes(updatedDataBytes).await()
            onSuccess.invoke()
        } catch (e: Exception) {
            onFailure.invoke("Произошла ошибка при сохранении данных в Firebase: ${e.message}")
        }
    }

}