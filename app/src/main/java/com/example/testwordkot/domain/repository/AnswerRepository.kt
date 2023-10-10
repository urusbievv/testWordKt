package com.example.testwordkot.domain.repository

import com.example.testwordkot.domain.model.WordDomain

interface AnswerRepository {

    suspend fun saveAnswersToFile(
        userEmail: String,
        newData: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )


}