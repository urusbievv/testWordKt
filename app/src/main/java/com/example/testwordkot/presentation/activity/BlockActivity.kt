package com.example.testwordkot.presentation.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testwordkot.R
import com.example.testwordkot.data.model.Word
import com.example.testwordkot.data.repository.AnswerRepositoryImpl
import com.example.testwordkot.data.repository.WordRepositoryImpl
import com.example.testwordkot.data.storage.repository.AnswerFirebaseStorage
import com.example.testwordkot.data.storage.repository.WordFirebaseStorage
import com.example.testwordkot.domain.usecase.AnswerSendUseCase
import com.example.testwordkot.domain.usecase.WordGetUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class BlockActivity : AppCompatActivity() {


    private val wordStorage by lazy { WordFirebaseStorage(applicationContext) }
    private val wordRepository by lazy { WordRepositoryImpl(wordStorage) }
    private val wordSendUseCase by lazy { WordGetUseCase(wordRepository) }

    private val answerStorage by lazy { AnswerFirebaseStorage() }
    private val answerRepository by lazy { AnswerRepositoryImpl(answerStorage) }
    private val answerSendUseCase by lazy { AnswerSendUseCase(answerRepository) }


    private lateinit var association1EditText: EditText
    private lateinit var association2EditText: EditText
    private lateinit var association3EditText: EditText
    private lateinit var association4EditText: EditText
    private lateinit var association5EditText: EditText

    private lateinit var btnNext: Button
    private lateinit var btnBack: Button
    private lateinit var btnImageBack: ImageButton

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
            wordList = wordSendUseCase.execute()
            associationsList = ArrayList()
            currentWordIndex = 0
            wordTextView.text = wordList[currentWordIndex].word
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
        btnImageBack = findViewById(R.id.imageButton4)

    }

    private fun setButtonClickListeners(){
        btnNext.setOnClickListener{showNextWord()}
        btnBack.setOnClickListener{showBackWord()}
        btnImageBack.setOnClickListener { finish() }
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


    private fun checkFields(): Boolean {
        val association1Text: String = association1EditText.text.toString().trim()
        val association2Text: String = association2EditText.text.toString().trim()
        val association3Text: String = association3EditText.text.toString().trim()
        val association4Text: String = association4EditText.text.toString().trim()
        val association5Text: String = association5EditText.text.toString().trim()

        return (association1Text.isNotEmpty() && association2Text.isNotEmpty() && association3Text.isNotEmpty()
                && association4Text.isNotEmpty() && association5Text.isNotEmpty())
    }

    private fun updateAssociationsList() {
        for (i in wordList.indices) {
            val association1 = association1EditText.text.toString()
            val association2 = association2EditText.text.toString()
            val association3 = association3EditText.text.toString()
            val association4 = association4EditText.text.toString()
            val association5 = association5EditText.text.toString()

            // Добавляем ассоциации
            associationsList.add(association1)
            associationsList.add(association2)
            associationsList.add(association3)
            associationsList.add(association4)
            associationsList.add(association5)
        }
    }

    private fun showNextWord(){
        lifecycleScope.launch {
            if (checkFields()) {
                updateAssociationsList()
                currentWordIndex++
                if (currentWordIndex < wordList.size) {
                    wordTextView.text = wordList[currentWordIndex].word
                    association1EditText.setText("")
                    association2EditText.setText("")
                    association3EditText.setText("")
                    association4EditText.setText("")
                    association5EditText.setText("")
                } else {
                    answerSendUseCase.execute(
                        userEmail = auth.currentUser?.email ?: "",
                        wordList = wordList,
                        associationsList = associationsList,
                        onSuccess = {
                            showToast("Ответы успешно сохранены")
                            goToMapActivity()
                        },
                        onFailure = { errorMessage ->
                            showToast("Ошибка сохранения ответов: $errorMessage")
                        }
                    )
                }
            } else {
                showToast("Заполните все ячейки !!!")
            }
        }
    }



    private fun showBackWord() {
        if (currentWordIndex > 0 && currentWordIndex * 6 < wordList.size) {
            currentWordIndex--
            wordTextView.text = wordList[currentWordIndex].word
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

    private fun sanitizeEmail(email: String): String {
        return email.replace(".", "_").replace("@", "_")
    }

    private fun goToMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
        playSound()
        finish()
    }
}

