package com.example.testwordkot.data.repository

import com.example.testwordkot.data.model.Word
import com.example.testwordkot.data.storage.repository.WordFirebaseStorage
import com.example.testwordkot.domain.repository.WordRepository

class WordRepositoryImpl(private val wordStorage: WordFirebaseStorage): WordRepository {


    override fun loadWordsFromFile(): List<Word> {
        return wordStorage.get()
    }


}