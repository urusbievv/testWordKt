package com.example.testwordkot.domain.usecase

import com.example.testwordkot.data.model.Word
import com.example.testwordkot.domain.repository.AnswerRepository

class AnswerSendUseCase(private val answerRepository: AnswerRepository) {

    suspend fun execute(userEmail: String,
                        wordList: List<Word>,
                        associationsList: List<String>,
                        onSuccess: () -> Unit,
                        onFailure: (String) -> Unit){

        return answerRepository.saveAnswersToFile(userEmail, wordList, associationsList, onSuccess, onFailure)
    }
}