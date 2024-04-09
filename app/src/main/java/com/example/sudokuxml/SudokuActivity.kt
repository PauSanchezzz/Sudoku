package com.example.sudokuxml

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
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
    private lateinit var checkBTN: Button
    private var gameBoard: SudokuBoard? = null
    public var gameBoardMan: BoardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sudoku)

        gameBoard = findViewById(R.id.SudokuBoard)
        gameBoardMan = gameBoard!!.getSolver()

        val difficult = intent.getStringExtra(DIFFICULT_LEVEL)

        txtTimer = findViewById(R.id.txtTimer)
        toolbar = findViewById<View>(R.id.sudokuToolBar) as Toolbar
        checkBTN = findViewById(R.id.check)

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.title = "Dificultad Seleccionada: $difficult"
        }
        TimeGame()
        fillBoard()

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
            "Facil" -> 300000
            "Intermedio" -> 150000
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

    private fun fillBoard() {

        val difficult = intent.getStringExtra(DIFFICULT_LEVEL)

        val quickTip: Float = when (difficult) {
            "Facil" -> 0.2F
            "Intermedio" -> 0.1F
            "Avanzado" -> 0.1F
            else -> 0F
        }

        val numbers = (1..9).toMutableList()

        val random = kotlin.random.Random
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (random.nextDouble() <= quickTip) {
                    numbers.shuffle()
                    val randomNumber = numbers[0]
                    if (gameBoardMan!!.isValid(i, j, randomNumber)) {
                        gameBoardMan!!.board[i][j] = randomNumber
                        gameBoardMan!!.editable[i][j] = false
                    }
                }
            }
        }
    }

    fun solve(view: View?) {
        if (checkBTN.getText().toString() == "Check") {
            gameBoardMan!!.getEmptyBoxIndexes()

            val solveBoardThread = SolveBoardThread()

            Thread(solveBoardThread).start()
            val solveHelper = gameBoardMan!!.solveHelper(0, 0, gameBoard!!)
            if (!solveHelper) {
                Toast.makeText(
                    applicationContext,
                    "¡No cumpliste las reglas! Perdiste.",
                    Toast.LENGTH_SHORT
                )
                    .show();
                val intent = Intent(this@SudokuActivity, MainActivity::class.java)
                Handler().postDelayed({
                    startActivity(intent)
                    finish()
                }, 200)

            }
            if (solveHelper) {
                Toast.makeText(
                    applicationContext,
                    "¡Felicitaciones! Ganaste.",
                    Toast.LENGTH_SHORT
                ).show();
                val intent = Intent(this@SudokuActivity, MainActivity::class.java)
                Handler().postDelayed({
                    startActivity(intent)
                    finish()
                }, 4000)
            }

            gameBoard!!.invalidate()
        }
    }

    inner class SolveBoardThread : Runnable {
        override fun run() {
            val solveHelper = gameBoardMan!!.solveHelper(0, 0, gameBoard!!)
            gameBoard!!.let { solveHelper }

        }

    }

}

