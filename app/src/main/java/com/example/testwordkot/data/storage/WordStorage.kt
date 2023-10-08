package com.example.testwordkot.data.storage

import com.example.testwordkot.data.model.Word

interface WordStorage {

    fun get(): List<Word>
}