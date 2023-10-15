package com.example.testwordkot.data.storage.repository


import com.example.testwordkot.data.storage.AnswerStorage
import com.example.testwordkot.data.storage.utils.ConstantsDATA.BLOCK_PREFIX
import com.example.testwordkot.data.storage.utils.ConstantsDATA.BLOCK_PREFIX_TITLE
import com.example.testwordkot.data.storage.utils.ConstantsDATA.BORDER_ANSWER
import com.example.testwordkot.data.storage.utils.ConstantsDATA.EMAIL
import com.example.testwordkot.data.storage.utils.ConstantsDATA.WORD_ERROR_MESSAGE
import com.example.testwordkot.data.storage.utils.ConstantsDATA.FILE_EXTENSION
import com.example.testwordkot.data.storage.utils.ConstantsDATA.NEW_LINE
import com.example.testwordkot.data.storage.utils.ConstantsDATA.POINT
import com.example.testwordkot.data.storage.utils.ConstantsDATA.SLASH
import com.example.testwordkot.data.storage.utils.ConstantsDATA.USERS_PACKAGE

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AnswerFirebaseStorage : AnswerStorage {
    private fun sanitizeEmail(email: String): String = email.replace(POINT, SLASH).replace(EMAIL,SLASH)

    /**
     * Сохраняет данные в Firebase Storage.
     *
     * @param userEmail Адрес электронной почты пользователя.
     * @param newData Новые данные для сохранения.
     * @param onSuccess Функция обратного вызова, вызываемая при успешном сохранении.
     * @param onFailure Функция обратного вызова, вызываемая при ошибке.
     */
    override suspend fun save(
        userEmail: String,
        newData: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            val sanitizedEmail = sanitizeEmail(userEmail)
            val fileName = "${BLOCK_PREFIX_TITLE}$sanitizedEmail${FILE_EXTENSION}"

            val answersRef = FirebaseStorage.getInstance().reference
                .child(USERS_PACKAGE)
                .child(sanitizedEmail)
                .child(BLOCK_PREFIX)
                .child(fileName)

            val existingData = try {
                String(answersRef.getBytes(Long.MAX_VALUE).await(), Charsets.UTF_8)
            } catch (e: Exception) {
                ""
            }
            val updatedData = if (existingData.isNotEmpty()) "$existingData${NEW_LINE}${BORDER_ANSWER}$NEW_LINE$newData" else newData
            answersRef.putBytes(updatedData.toByteArray()).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(WORD_ERROR_MESSAGE)
        }
    }

}