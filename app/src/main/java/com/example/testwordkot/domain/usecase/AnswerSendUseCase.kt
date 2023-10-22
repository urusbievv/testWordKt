package com.example.testwordkot.domain.usecase


import com.example.testwordkot.data.model.Word
import com.example.testwordkot.domain.repository.AnswerRepository
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.ANSWER_HEADER
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.ASSOCIATIONS
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.BLOCK_1_HEADER
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.COLON
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.DATA_PREPARATION_ERROR_MESSAGE
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.LEVEL_BLOCK_HEADER
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.NEW_LINE
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.POINT
import com.example.testwordkot.domain.usecase.utils.ConstantsDomain.WORD_DIV

class AnswerSendUseCase(private val answerRepository: AnswerRepository) {

    /**
     * Строит новые данные на основе списков слов и ассоциаций.
     *
     * @param wordList Список слов для включения в данные.
     * @param associationsList Список строк с ассоциациями для каждого слова.
     * @return Строка с новыми данными.
     */
    private fun buildNewData(wordList: List<Word>, associationsList: List<String>): String =
        buildString {
            val numberWords = 1..5
            val numberAssociation = 6
            append("$LEVEL_BLOCK_HEADER$NEW_LINE$BLOCK_1_HEADER$POINT $ANSWER_HEADER$NEW_LINE$NEW_LINE")
            // Перебор каждого слова
            wordList.forEachIndexed { wordIndex, word ->
                // Формирование списка ассоциаций для текущего слова
                val associations = (numberWords).mapNotNull { associationIndex ->
                    val indexInAssociationsList = wordIndex * numberAssociation + associationIndex
                    associationsList.getOrNull(indexInAssociationsList)
                }
                appendLine("$WORD_DIV$COLON ${word.word}")
                associations.forEach { association ->
                    appendLine("$ASSOCIATIONS$COLON $association")
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
            onFailure.invoke(DATA_PREPARATION_ERROR_MESSAGE)
        }
    }

}