package com.example.testwordkot.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.testwordkot.R
import com.example.testwordkot.activity.maps.MapActivity

class LevelActivity : AppCompatActivity() {

    private lateinit var easyLevel: Button
    private lateinit var averageLevel: Button
    private lateinit var highLevel: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
    }


    override fun onResume() {
        super.onResume()
        initView()
        setButtonClickListeners()
    }


    private fun initView(){
        easyLevel = findViewById(R.id.easyLevel)
        averageLevel = findViewById(R.id.averageLevel)
        highLevel = findViewById(R.id.highLevel)

    }

    private fun setButtonClickListeners(){
        easyLevel.setOnClickListener {
            startActivity(Intent(this@LevelActivity, MapActivity::class.java))
        }
//        averageLevel.setOnClickListener {
//            val intent = Intent(this@LevelActivity, MapAveActivity::class.java)
//            startActivity(intent)
//        }
//        highLevel.setOnClickListener {
//            val intent = Intent(this@LevelActivity, MapHighActivity::class.java)
//            startActivity(intent)
//        }
    }
}