package com.example.testwordkot.data.repository

import com.example.testwordkot.data.model.Word
import com.example.testwordkot.data.storage.AnswerStorage
import com.example.testwordkot.domain.repository.AnswerRepository

class AnswerRepositoryImpl(private val answerStorage: AnswerStorage): AnswerRepository {
    override suspend fun saveAnswersToFile(userEmail: String,
                                   wordList: List<Word>,
                                   associationsList: List<String>,
                                   onSuccess: () -> Unit,
                                   onFailure: (String) -> Unit) {
        return answerStorage.save(userEmail, wordList, associationsList, onSuccess, onFailure)
    }
}