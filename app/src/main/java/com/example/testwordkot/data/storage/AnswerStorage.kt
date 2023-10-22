package com.example.testwordkot.data.storage


interface AnswerStorage{

    suspend fun save(
        userEmail: String,
        newData: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}