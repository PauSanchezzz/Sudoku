package com.example.sudokuxml

class BoardManager() {
    lateinit var board: Array<IntArray>
    lateinit var editable: Array<BooleanArray>
    var selected_row = -1
    var selected_column = -1

    init {
        initializeBoard()
    }

    private fun initializeBoard() {
        board = Array(9) { IntArray(9) }
        initializeEditableArray()
    }

    private fun initializeEditableArray() {
        editable =
            Array(9) { BooleanArray(9) { true } }
    }

    fun setNumberPos(num: Int) {
        if (selected_row != -1 && selected_column != -1 && editable[selected_row - 1][selected_column - 1]) {
            if (board[selected_row - 1][selected_column - 1] == num) {
                board[selected_row - 1][selected_column - 1] = 0
            } else {
                board[selected_row - 1][selected_column - 1] = num
            }
        }
    }

    fun getBoardFromGetter(): Array<IntArray> {
        return board
    }

    fun getSelectedRow(): Int {
        return selected_row
    }

    fun getSelectedColumn(): Int {
        return selected_column
    }

    fun setSelectedRow(r: Int) {
        selected_row = r
    }

    fun setSelectedColumn(c: Int) {
        selected_column = c
    }
}