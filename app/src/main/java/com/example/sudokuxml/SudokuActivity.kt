package com.example.sudokuxml

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SudokuActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

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

        toolbar = findViewById<View>(R.id.sudokuToolBar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = "Dificultad Seleccionada: $difficult"
        }
    }

    companion object {
        const val DIFFICULT_LEVEL = "difficult_level"
    }


}

