package com.example.sudokuxml

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private lateinit var beginnerButton: Button
    private lateinit var intermediateButton: Button
    private lateinit var advancedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        beginnerButton = findViewById(R.id.beginnerButton)
        beginnerButton.setOnClickListener({ onClickLevel("easy") })
        intermediateButton = findViewById(R.id.intermediateButton)
        intermediateButton.setOnClickListener({ onClickLevel("intermediate") })
        advancedButton = findViewById(R.id.advancedButton)
        advancedButton.setOnClickListener({ onClickLevel("advanced") })

    }

    fun onClickLevel(difficult: String) {
        val intent = Intent(this, SudokuActivity::class.java)
        intent.putExtra(SudokuActivity.DIFFICULT_LEVEL, difficult)
        startActivity(intent)
    }
}