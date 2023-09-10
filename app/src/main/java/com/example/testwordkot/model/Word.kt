package com.example.testwordkot.model

import java.util.stream.Stream

data class Word(private var word: String, private var currentAssociations: List<String>) {

    fun getWord(): String = word
    fun getCorrectAssociations(): List<String> = currentAssociations


}