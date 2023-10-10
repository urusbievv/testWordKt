package com.example.testwordkot.domain.usecase


import com.example.testwordkot.data.model.Word
import com.example.testwordkot.domain.repository.AnswerRepository

class AnswerSendUseCase(private val answerRepository: AnswerRepository) {

    private fun buildNewData(wordList: List<Word>, associationsList: List<String>): String =
        buildString {
            append("Блок 1 (Легкий). Ответы\n\n")
            wordList.forEachIndexed { i, word ->
                val associations = (1..5).mapNotNull { j ->
                    val index = i * 6 + j
                    associationsList.getOrNull(index)
                }
                appendLine("Слово: ${word.word}")
                associations.forEach { association ->
                    appendLine("Ассоциации: $association")
                }
                appendLine()
            }
        }


    suspend fun execute(
        userEmail: String,
        wordList: List<Word>,
        associationsList: List<String>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            val newData = buildNewData(wordList, associationsList)
            answerRepository.saveAnswersToFile(userEmail, newData, onSuccess, onFailure)
        } catch (e: Exception) {
            onFailure.invoke("Произошла ошибка при подготовке данных: ${e.message}")
        }
    }

}