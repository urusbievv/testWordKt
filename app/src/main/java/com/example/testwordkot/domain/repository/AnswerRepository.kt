package com.example.testwordkot.domain.repository

import com.example.testwordkot.data.model.Word

interface AnswerRepository {

    suspend fun saveAnswersToFile(
        userEmail: String,
        wordList: List<Word>,
        associationsList: List<String>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}