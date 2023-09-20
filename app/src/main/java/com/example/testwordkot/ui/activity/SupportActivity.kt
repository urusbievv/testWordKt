package com.example.testwordkot.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SupportActivity : AppCompatActivity() {

    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var btnImageBack: ImageButton

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
    }

    override fun onResume() {
        super.onResume()
        databaseRef = FirebaseDatabase.getInstance().getReference("support_messages")
        initView()
        btnSend.setOnClickListener { sendMessage() }
        btnImageBack.setOnClickListener { finish() }
    }

    private fun initView(){
        etMessage = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_send)
        btnImageBack = findViewById(R.id.imageButton4)
    }

    private fun sendMessage(){
        val message: String = etMessage.text.toString().trim()
        if(message.isNotEmpty()){
            val messageId = databaseRef.push().key
            databaseRef.child(messageId!!).setValue(message)
            etMessage.setText("")
        }
    }

}