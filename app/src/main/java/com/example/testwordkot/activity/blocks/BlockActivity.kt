package com.example.testwordkot.activity.blocks

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.example.testwordkot.activity.maps.MapActivity
import com.example.testwordkot.model.Word
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class BlockActivity : AppCompatActivity() {

    private lateinit var association1EditText: EditText
    private lateinit var association2EditText: EditText
    private lateinit var association3EditText: EditText
    private lateinit var association4EditText: EditText
    private lateinit var association5EditText: EditText

    private lateinit var btnNext: Button
    private lateinit var btnBack: Button

    private lateinit var wordTextView: TextView
    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var wordList: List<Word>
    private var associationsList: MutableList<String> = mutableListOf()
    private lateinit var selectedWords: Set<String>

    private lateinit var auth: FirebaseAuth

    private var currentWordIndex: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)
    }

    override fun onResume() {
        super.onResume()
        auth = FirebaseAuth.getInstance()
        initView()
        selectedWords = HashSet()
        if (assets != null) {
            wordList = loadWordsFromFile("words.txt")
            associationsList = ArrayList()
            currentWordIndex = 0
            wordTextView.text = wordList[currentWordIndex].getWord()
            setButtonClickListeners()
            association1EditText.addTextChangedListener(textWatcher)
            association2EditText.addTextChangedListener(textWatcher)
            association3EditText.addTextChangedListener(textWatcher)
            association4EditText.addTextChangedListener(textWatcher)
            association5EditText.addTextChangedListener(textWatcher)
        } else {
            showToast("Ошибка при загрузке данных")
            finish()
        }
    }

    private fun initView(){
        wordTextView = findViewById(R.id.wordTextView)
        association1EditText = findViewById(R.id.association1EditText)
        association2EditText = findViewById(R.id.association2EditText)
        association3EditText = findViewById(R.id.association3EditText)
        association4EditText = findViewById(R.id.association4EditText)
        association5EditText = findViewById(R.id.association5EditText)
        mediaPlayer = MediaPlayer.create(this, R.raw.tadam)

        btnNext = findViewById(R.id.nextButton)
        btnBack = findViewById(R.id.backButton)
    }

    private fun setButtonClickListeners(){
        btnNext.setOnClickListener{showNextWord()}
        btnBack.setOnClickListener{showBackWord()}
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playSound() {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()

    }


    private fun checkFields(): Boolean{
        val association1Text: String = association1EditText.text.toString().trim()
        val association2Text: String = association2EditText.text.toString().trim()
        val association3Text: String = association3EditText.text.toString().trim()
        val association4Text: String = association4EditText.text.toString().trim()
        val association5Text: String = association5EditText.text.toString().trim()

        val isFieldsFilled = (association1Text.isNotEmpty() && association2Text.isNotEmpty() && association3Text.isNotEmpty()
                && association4Text.isNotEmpty() && association5Text.isNotEmpty())
        return isFieldsFilled
    }

    private fun updateAssociationsList() {
        for (i in wordList.indices) {
            val association1 = association1EditText.text.toString()
            val association2 = association2EditText.text.toString()
            val association3 = association3EditText.text.toString()
            val association4 = association4EditText.text.toString()
            val association5 = association5EditText.text.toString()

            // Добавляем ассоциации в associationsList в соответствии с индексом слова
            associationsList.add(association1)
            associationsList.add(association2)
            associationsList.add(association3)
            associationsList.add(association4)
            associationsList.add(association5)
        }
    }

    private fun showNextWord(){
        if (checkFields()){
            updateAssociationsList()
            currentWordIndex++
            if (currentWordIndex < wordList.size){
                wordTextView.text = wordList[currentWordIndex].getWord()
                association1EditText.setText("")
                association2EditText.setText("")
                association3EditText.setText("")
                association4EditText.setText("")
                association5EditText.setText("")
            }else{
                saveAnswersToFile()
            }
        } else{
            showToast("Заполните все ячейки !!!")
        }
    }



    private fun showBackWord() {
        if (currentWordIndex > 0 && currentWordIndex * 6 < wordList.size) {
            currentWordIndex--
            wordTextView.text = wordList[currentWordIndex].getWord()
            association1EditText.setText(associationsList[currentWordIndex * 6])
            association2EditText.setText(associationsList[currentWordIndex * 6 + 1])
            association3EditText.setText(associationsList[currentWordIndex * 6 + 2])
            association4EditText.setText(associationsList[currentWordIndex * 6 + 3])
            association5EditText.setText(associationsList[currentWordIndex * 6 + 4])
        }
    }

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {checkFields()}
    }

    // Загрузка слов из файла
    private fun loadWordsFromFile(fileName: String): List<Word> {
        val wordList: MutableList<Word> = mutableListOf()
        val assetManager = assets
        try {
            val inputStream = assetManager.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lines = mutableListOf<String>()

            // Читаем все строки из файла и сохраняем их в список
            var line: String? = reader.readLine()
            while (line != null) {
                lines.add(line.trim())
                line = reader.readLine()
            }
            inputStream.close()

            // Перемешиваем список строк
            lines.shuffle()

            val wordsToAdd = minOf(2, lines.size)
            for (i in 0 until wordsToAdd) {
                val word = Word(lines[i], emptyList())
                wordList.add(word)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("Ошибка при загрузке данных")
        }
        return wordList
    }

    // Сохранение ответов в файл
    private fun saveAnswersToFile() {
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

        if (currentUser == null || userEmail == null) {
            return
        }

        val sanitizedEmail = sanitizeEmail(userEmail)
        val fileName = "Блок_1_$sanitizedEmail.txt"
        val storageRef = FirebaseStorage.getInstance().reference
        val userRef = storageRef.child("users").child(sanitizedEmail)
        val answersRef = userRef.child("Блок_1").child(fileName)

        val newData = buildString {
            append("Блок 1 (Легкий). Ответы\n\n")
            for (i in wordList.indices) {
                val word = wordList[i].getWord()
                val associations = (1..5).mapNotNull { j ->
                    val index = i * 6 + j
                    if (index < associationsList.size) associationsList[index] else null
                }

                append("Слово: $word\n")
                associations.forEach { association ->
                    append("Ассоциации: $association\n")
                }
                append("\n")
            }
        }

        val newDataBytes = newData.toByteArray()

        answersRef.getBytes(Long.MAX_VALUE)
            .addOnSuccessListener { existingDataBytes ->
                val existingData = String(existingDataBytes, Charsets.UTF_8)

                val updatedData = if (existingData.isNotEmpty()) {
                    "$existingData\n----------------\n$newData"
                } else {
                    newData
                }

                val updatedDataBytes = updatedData.toByteArray()

                answersRef.putBytes(updatedDataBytes)
                    .addOnSuccessListener {
                        val message = if (existingData.isNotEmpty()) {
                            "Ответы были обновлены."
                        } else {
                            "Ответы успешно отправлены."
                        }
                        showToast(message)
                        goToMapActivity()
                    }
                    .addOnFailureListener {
                        showToast("Ошибка сохранения данных: ${it.message}")
                    }
            }
            .addOnFailureListener { exception ->
                showToast("Ошибка чтения существующих данных: ${exception.message}")


                answersRef.putBytes(newDataBytes)
                    .addOnSuccessListener {
                        showToast("Ответы отправлены.")
                        goToMapActivity()
                    }
                    .addOnFailureListener {
                        showToast("Ошибка создания нового файла: ${it.message}")
                    }
            }
    }








    private fun sanitizeEmail(email: String): String {
        return email.replace(".", "_").replace("@", "_")
    }

    private fun goToMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
        playSound()
        finish()
    }

    fun goBack() {
        finish()
    }
}

