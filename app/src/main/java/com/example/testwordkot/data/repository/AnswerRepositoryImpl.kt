package com.example.testwordkot.data.repository


import com.example.testwordkot.data.storage.AnswerStorage
import com.example.testwordkot.domain.repository.AnswerRepository

class AnswerRepositoryImpl(private val answerStorage: AnswerStorage):
    AnswerRepository {
    override suspend fun saveAnswersToFile(
        userEmail: String,
        newData: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            answerStorage.save(
                userEmail,
                newData,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        } catch (e: Exception) {
            onFailure.invoke("Произошла ошибка при сохранении данных: ${e.message}")
        }
    }

}