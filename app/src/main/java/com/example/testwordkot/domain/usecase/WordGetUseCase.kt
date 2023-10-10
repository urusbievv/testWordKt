package com.example.testwordkot.domain.usecase

import com.example.testwordkot.data.model.Word
import com.example.testwordkot.domain.repository.WordRepository

class WordGetUseCase(private val wordRepository: WordRepository) {
    fun execute(): List<Word> = wordRepository.loadWordsFromFile()

}