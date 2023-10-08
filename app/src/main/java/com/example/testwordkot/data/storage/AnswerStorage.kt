package com.example.testwordkot.data.storage

import com.example.testwordkot.data.model.Word

interface AnswerStorage{

    suspend fun save(userEmail: String,
             wordList: List<Word>,
             associationsList: List<String>,
             onSuccess: () -> Unit,
             onFailure: (String) -> Unit)
}