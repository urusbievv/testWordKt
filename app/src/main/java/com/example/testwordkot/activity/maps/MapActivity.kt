package com.example.testwordkot.activity.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.example.testwordkot.activity.LevelActivity
import com.example.testwordkot.activity.MainActivity
import com.example.testwordkot.activity.SupportActivity
import com.example.testwordkot.activity.blocks.BlockActivity
import com.example.testwordkot.activity.blocks.ComingSoonActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MapActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageFireBase: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var textEmail: TextView
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
        getEmail()
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
        textEmail = findViewById(R.id.txt_email);
        btnLevel = findViewById(R.id.btn_level);
    }

    private fun setButtonClickListeners(){
        block1.setOnClickListener {
            startActivity(Intent(this@MapActivity, BlockActivity::class.java))
        }
        block2.setOnClickListener {
            startActivity(Intent(this@MapActivity, ComingSoonActivity::class.java))
        }
        block3.setOnClickListener {
            startActivity(Intent(this@MapActivity, ComingSoonActivity::class.java))
        }
        block4.setOnClickListener {
            startActivity(Intent(this@MapActivity, ComingSoonActivity::class.java))
        }
        block5.setOnClickListener {
            startActivity(Intent(this@MapActivity, ComingSoonActivity::class.java))
        }
        btnSupport.setOnClickListener{
            startActivity(Intent(this@MapActivity,SupportActivity::class.java))
        }
        btnLevel.setOnClickListener {
            startActivity(Intent(this@MapActivity,LevelActivity::class.java))
        }
        exitProfile.setOnClickListener {
            startActivity(Intent(this@MapActivity,MainActivity::class.java))
        }
    }

    private fun getEmail(){
        val email = intent.getStringExtra("email")
        textEmail.text = email
    }

}