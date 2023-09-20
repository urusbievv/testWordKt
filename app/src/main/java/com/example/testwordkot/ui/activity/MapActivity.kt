package com.example.testwordkot.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MapActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageFireBase: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var exitProfile: TextView
    private lateinit var btnLevel: Button
    private lateinit var btnViewFile: Button
    private lateinit var btnSupport: ImageView

    private lateinit var block1: LinearLayout
    private lateinit var block2: LinearLayout
    private lateinit var block3: LinearLayout
    private lateinit var block4: LinearLayout
    private lateinit var block5: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }

    override fun onResume() {
        super.onResume()
        auth = FirebaseAuth.getInstance()
        storageFireBase = FirebaseStorage.getInstance()
        storageReference = storageFireBase.reference
        initView()
        setButtonClickListeners()
    }

    private fun initView(){
        block1 = findViewById(R.id.block_1)
        block2 = findViewById(R.id.block_2)
        block3 = findViewById(R.id.block_3)
        block4 = findViewById(R.id.block_4)
        block5 = findViewById(R.id.block_5)
        btnViewFile = findViewById(R.id.view_files_button)
        exitProfile = findViewById(R.id.exit_profile)
        btnSupport = findViewById(R.id.btn_support)
        btnLevel = findViewById(R.id.btn_level);
    }

    private fun setButtonClickListeners() { // упрощенный
        listOf(block1, block2, block3, block4, block5, btnSupport, btnLevel, exitProfile).forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(clicked: View) {
        val intent: Intent? = when(clicked.id) {
            block1.id -> Intent(this@MapActivity, BlockActivity::class.java)
            block2.id, block3.id, block4.id, block5.id  ->
                Intent(this@MapActivity, ComingSoonActivity::class.java)
            btnSupport.id  -> Intent(this@MapActivity,SupportActivity::class.java)
            btnLevel.id  -> Intent(this@MapActivity,LevelActivity::class.java)
            exitProfile.id -> Intent(this@MapActivity,MainActivity::class.java)
            else -> null
        }

        if (intent != null) {
            startActivity(intent)
        }
    }

}