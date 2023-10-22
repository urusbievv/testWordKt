package com.example.testwordkot.data.storage.repository

import android.content.Context
import com.example.testwordkot.data.model.Word
import com.example.testwordkot.data.storage.WordStorage
import com.example.testwordkot.data.storage.utils.ConstantsDATA.FILE_WORDS
import com.example.testwordkot.data.storage.utils.ConstantsDATA.EASY_LEVEL_BLOCK_1_NUMBER_WORDS
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class WordFirebaseStorage(private val context: Context) : WordStorage {
    override fun get(): List<Word> {
        val wordList: MutableList<Word> = mutableListOf()
        try {
            context.assets.open(FILE_WORDS).use { inputStream ->
                val lines = BufferedReader(InputStreamReader(inputStream)).readLines().map { it.trim() }
                // Перемешивание строк в случайном порядке
                val shuffledLines = lines.shuffled()
                val wordsToAdd = minOf(EASY_LEVEL_BLOCK_1_NUMBER_WORDS, shuffledLines.size)
                wordList.addAll(shuffledLines.take(wordsToAdd).map { Word(it, emptyList()) })
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return wordList
    }
}