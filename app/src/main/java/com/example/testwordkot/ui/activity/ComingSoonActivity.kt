package com.example.testwordkot.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.testwordkot.R

class ComingSoonActivity : AppCompatActivity() {

    private lateinit var btnImageBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)
    }

    override fun onResume() {
        super.onResume()
        initView()
        setButtonClickListeners()
    }

    private fun initView(){
        btnImageBack = findViewById(R.id.imageButton4)
    }
    private fun setButtonClickListeners(){
        btnImageBack.setOnClickListener { finish() }
    }




}