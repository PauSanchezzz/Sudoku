package com.example.sudokuxml

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SudokuActivity : AppCompatActivity() {
    private lateinit var txtDif: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sudoku)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SudokuXML)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val difficult = intent.getStringExtra(DIFFICULT_LEVEL)

        txtDif = findViewById<TextView>(R.id.textView2)
        txtDif.setText(difficult)
    }

    companion object {
        const val DIFFICULT_LEVEL = "difficult_level"
    }


}

