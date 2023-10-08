package com.example.testwordkot.domain.repository

import com.example.testwordkot.data.model.Word

interface WordRepository {

    fun loadWordsFromFile(): List<Word>

}