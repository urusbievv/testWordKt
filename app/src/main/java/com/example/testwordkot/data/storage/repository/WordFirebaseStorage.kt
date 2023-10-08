package com.example.testwordkot.data.storage.repository

import android.content.Context
import com.example.testwordkot.data.model.Word
import com.example.testwordkot.data.storage.WordStorage

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class WordFirebaseStorage(private val context: Context) : WordStorage {


    override fun get(): List<Word> {
        val wordList: MutableList<Word> = mutableListOf()
        val assetManager = context.assets

        try {
            val inputStream = assetManager.open("words.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lines = mutableListOf<String>()

            var line: String? = reader.readLine()
            while (line != null) {
                lines.add(line.trim())
                line = reader.readLine()
            }
            inputStream.close()

            lines.shuffle()

            val wordsToAdd = minOf(2, lines.size)
            for (i in 0 until wordsToAdd) {
                val word = Word(lines[i], emptyList())
                wordList.add(word)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return wordList
    }
}