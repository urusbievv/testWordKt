package com.example.testwordkot.data.model



data class Word(private val word: String, private val currentAssociations: List<String>) {

    fun getWord(): String = word

}