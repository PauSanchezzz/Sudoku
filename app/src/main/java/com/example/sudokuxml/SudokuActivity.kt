package com.example.sudokuxml

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Locale

class SudokuActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private lateinit var toolbar: Toolbar
    private lateinit var txtTimer: TextView
    private var gameBoard: SudokuBoard? = null
    private var gameBoardMan: BoardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sudoku)

        gameBoard = findViewById(R.id.SudokuBoard)
        gameBoardMan = gameBoard!!.getSolver()

        val difficult = intent.getStringExtra(DIFFICULT_LEVEL)

        txtTimer = findViewById(R.id.txtTimer)
        toolbar = findViewById<View>(R.id.sudokuToolBar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.title = "Dificultad Seleccionada: $difficult"
        }
        TimeGame()
    }

    companion object {
        const val DIFFICULT_LEVEL = "difficult_level"
    }

    fun BTNOnePress(view: View) {
        gameBoardMan?.setNumberPos(1)
        gameBoard?.invalidate()
        soundOne()
    }

    fun BTNTwoPress(view: View) {
        gameBoardMan?.setNumberPos(2)
        gameBoard?.invalidate()
        soundTwo()
    }

    fun BTNThreePress(view: View) {
        gameBoardMan?.setNumberPos(3)
        gameBoard?.invalidate()
        soundOne()
    }

    fun BTNFourPress(view: View) {
        gameBoardMan?.setNumberPos(4)
        gameBoard?.invalidate()
        soundTwo()
    }

    fun BTNFivePress(view: View) {
        gameBoardMan?.setNumberPos(5)
        gameBoard?.invalidate()
        soundOne()
    }

    fun BTNSixPress(view: View) {
        gameBoardMan?.setNumberPos(6)
        gameBoard?.invalidate()
        soundTwo()
    }

    fun BTNSevenPress(view: View) {
        gameBoardMan?.setNumberPos(7)
        gameBoard?.invalidate()
        soundOne()
    }

    fun BTNEightPress(view: View) {
        gameBoardMan?.setNumberPos(8)
        gameBoard?.invalidate()
        soundTwo()
    }

    fun BTNNinePress(view: View) {
        gameBoardMan?.setNumberPos(9)
        gameBoard?.invalidate()
        soundOne()
    }

    private fun soundOne() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.sound1)
        mediaPlayer.start()
    }

    private fun soundTwo() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.sound2)
        mediaPlayer.start()
    }

    private fun TimeGame() {
        val difficult = intent.getStringExtra(DIFFICULT_LEVEL)

        val time: Long = when (difficult) {
            "Facil" -> 60000
            "Intermedio" -> 30000
            "Avanzado" -> 15000
            else -> 0
        }

        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000).toInt() / 60
                val seconds = (millisUntilFinished / 1000).toInt() % 60

                val timeLeftFormatted =
                    String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

                txtTimer.text = "Tiempo restante: $timeLeftFormatted"
            }

            override fun onFinish() {
                Toast.makeText(applicationContext, "¡Tiempo agotado! Perdiste.", Toast.LENGTH_SHORT)
                    .show();
                val intent = Intent(this@SudokuActivity, MainActivity::class.java)
                Handler().postDelayed({
                    startActivity(intent)
                    finish()
                }, 100)
            }
        }
        (countDownTimer as CountDownTimer).start()
    }
}

