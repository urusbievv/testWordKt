package com.example.testwordkot.data.storage.repository

import com.example.testwordkot.data.model.Word
import com.example.testwordkot.data.storage.AnswerStorage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AnswerFirebaseStorage : AnswerStorage {


    private fun sanitizeEmail(email: String): String {
        return email.replace(".", "_").replace("@", "_")
    }

    override suspend fun save(
        userEmail: String,
        wordList: List<Word>,
        associationsList: List<String>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {


        try {
            val sanitizedEmail = sanitizeEmail(userEmail)
            val fileName = "Блок_1_$sanitizedEmail.txt"
            val storageRef = FirebaseStorage.getInstance().reference
            val userRef = storageRef.child("users").child(sanitizedEmail)
            val answersRef = userRef.child("Блок_1").child(fileName)

            val newData = buildString {
                append("Блок 1 (Легкий). Ответы\n\n")
                for (i in wordList.indices) {
                    val word = wordList[i].word
                    val associations = (1..5).mapNotNull { j ->
                        val index = i * 6 + j
                        if (index < associationsList.size) associationsList[index] else null
                    }

                    append("Слово: $word\n")
                    associations.forEach { association ->
                        append("Ассоциации: $association\n")
                    }
                    append("\n")
                }
            }

            val newDataBytes = newData.toByteArray()

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

            val message = if (existingData.isNotEmpty()) {
                "Ответы были обновлены."
            } else {
                "Ответы успешно отправлены."
            }
            onSuccess.invoke()

        } catch (e: Exception) {
            onFailure.invoke("Ошибка сохранения данных: ${e.message}")
        }
    }

}