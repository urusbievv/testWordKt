package com.example.testwordkot.data.storage.repository


import com.example.testwordkot.data.storage.AnswerStorage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await


private const val BLOCK_PREFIX_TITLE = "Блок_1_"
private const val BLOCK_PREFIX = "Блок_1"
private const val FILE_EXTENSION = ".txt"
private const val POINT = "."
private const val SLASH = "_"
private const val EMAIL = "@"
private const val USERS = "users"


class AnswerFirebaseStorage : AnswerStorage {
    private fun sanitizeEmail(email: String): String = email.replace(POINT, SLASH).replace(EMAIL, SLASH)

    override suspend fun save(
        userEmail: String,
        newData: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            val sanitizedEmail = sanitizeEmail(userEmail)
            val fileName = "${BLOCK_PREFIX_TITLE}$sanitizedEmail$FILE_EXTENSION"

            val answersRef = FirebaseStorage.getInstance().reference
                .child(USERS)
                .child(sanitizedEmail)
                .child(BLOCK_PREFIX)
                .child(fileName)

            val existingData = try {
                String(answersRef.getBytes(Long.MAX_VALUE).await(), Charsets.UTF_8)
            } catch (e: Exception) {
                ""
            }

            val updatedData = if (existingData.isNotEmpty()) "$existingData\n----------------\n$newData" else newData

            answersRef.putBytes(updatedData.toByteArray()).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure("Произошла ошибка при сохранении данных в Firebase: ${e.message}")
        }
    }

}