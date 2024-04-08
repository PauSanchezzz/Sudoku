package com.example.sudokuxml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class SudokuBoard(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val sqrtSize = 3
    private val size = 9
    private var cellSize = 0
    private var cellSizePixels = 0F
    private var noteSizePixels = 0F
    private var selectedRow = -1
    private var selectedCol = -1
    private val gameBoard = BoardManager()
    val letterPaintBounds = Rect()

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#71B8B0")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#CDE7E4")
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    private val startingCellTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        typeface = Typeface.DEFAULT_BOLD
    }

    private val noteTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = min(widthMeasureSpec, heightMeasureSpec)
        val dimension =
            min(this.measuredWidth.toDouble(), this.measuredHeight.toDouble())
                .toInt()
        cellSize = dimension / 9
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()
        updateMeasurements(width)
        fillCells(canvas)
        drawLines(canvas)
        drawNumbers(canvas)
    }

    private fun updateMeasurements(width: Int) {
        cellSizePixels = width / size.toFloat()
        noteSizePixels = cellSizePixels / sqrtSize.toFloat()
        noteTextPaint.textSize = cellSizePixels / sqrtSize.toFloat()
        textPaint.textSize = cellSizePixels / 1.5F
        startingCellTextPaint.textSize = cellSizePixels / 1.5F
    }

    private fun fillCells(canvas: Canvas) {
        if (selectedRow == -1 || selectedCol == -1) return
        if (gameBoard.getSelectedColumn() !== -1 && gameBoard.getSelectedRow() !== -1) {
            for (r in 0..size) {
                for (c in 0..size) {
                    if (r == selectedRow && c == selectedCol) {
                        fillCell(canvas, r, c, selectedCellPaint)
                    } else if (r == selectedRow || c == selectedCol) {
                        fillCell(canvas, r, c, conflictingCellPaint)
                    } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize) {
                        fillCell(canvas, r, c, conflictingCellPaint)
                    }
                }
            }
        }
        invalidate()
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(
            c * cellSizePixels,
            r * cellSizePixels,
            (c + 1) * cellSizePixels,
            (r + 1) * cellSizePixels, paint
        )
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            canvas.drawLine(
                i * cellSizePixels,
                0F,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )

            canvas.drawLine(
                0F,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }

    private fun drawNumbers(canvas: Canvas) {
        for (r in 0..8) {
            for (c in 0..8) {
                if (gameBoard.getBoardFromGetter().get(r).get(c) != 0) {
                    val text = gameBoard.getBoardFromGetter()[r][c].toString()

                    var width: Float
                    var height: Float
                    noteTextPaint.getTextBounds(text, 0, text.length, letterPaintBounds)
                    width = noteTextPaint.measureText(text)
                    height = letterPaintBounds.height().toFloat()

                    val textX = c * cellSize + (cellSize - width) / 2
                    val textY = r * cellSize + cellSize - (cellSize - height) / 2
                    canvas.drawText(text, textX, textY, noteTextPaint)
                }
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                gameBoard.setSelectedRow(Math.ceil((event.y / cellSizePixels).toDouble()).toInt());
                gameBoard.setSelectedColumn(Math.ceil((event.x / cellSizePixels).toDouble()).toInt())
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        selectedRow = (y / cellSizePixels).toInt()
        selectedCol = (x / cellSizePixels).toInt()
        invalidate()
    }

    fun getSolver(): BoardManager {
        return gameBoard
    }
}