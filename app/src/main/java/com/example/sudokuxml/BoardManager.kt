package com.example.sudokuxml

class BoardManager() {
    var board: Array<IntArray>
    var emptyBoxIndex: ArrayList<ArrayList<Any>> = ArrayList()
    var editable: Array<BooleanArray>
    var selectedNumber: Int = 0
    var selected_row = -1
    var selected_column = -1

    init {
        board = Array(9) { IntArray(9) }
        editable = Array(9) { BooleanArray(9) { true } }

        for (i in 0 until 9) {
            for (j in 0 until 9) {
                editable[i][j] = true
            }
        }
    }

    fun numberOfPosition(row: Int, col: Int): Int {
        return board[row][col]
    }

    fun isValid(row: Int, col: Int, num: Int): Boolean {
        for (i in 0..8) {
            if (board[row][i] == num || board[i][col] == num) {
                return false
            }
        }
        val boxStartRow = row - row % 3
        val boxStartCol = col - col % 3
        for (r in boxStartRow until boxStartRow + 3) {
            for (c in boxStartCol until boxStartCol + 3) {
                if (board[r][c] == num) {
                    return false
                }
            }
        }
        return true
    }

    fun getEmptyBoxIndexes() {
        for (r in 0..8) {
            for (c in 0..8) {
                if (board[r][c] == 0) {
                    this.emptyBoxIndex!!.add(ArrayList<Any>())
                    this.emptyBoxIndex!!.get(this.emptyBoxIndex!!.size - 1).add(r)
                    this.emptyBoxIndex!!.get(this.emptyBoxIndex!!.size - 1).add(c)
                }
            }
        }
    }

    private fun check(row: Int, col: Int): Boolean {
        if (board[row][col] > 0) {
            for (i in 0..8) {
                if (board[i][col] == board[row][col] && row != i) {
                    return false
                }
                if (board[row][i] == board[row][col] && col != i) {
                    return false
                }
            }
            val boxRow = row / 3
            val boxCol = col / 3
            for (r in boxRow * 3 until boxRow * 3 + 3) {
                for (c in boxCol * 3 until boxCol * 3 + 3) {
                    if (board[r][c] == board[row][col] && row != r && col != c) {
                        return false
                    }
                }
            }
        }
        return true
    }

     fun solveHelper(row: Int, col: Int, display: SudokuBoard): Boolean {
        if (row == 9) {
            return true
        }
        val nextRow = if (col == 8) row + 1 else row
        val nextCol = (col + 1) % 9
        if (board[row][col] != 0) {
            if (check(row, col)) {
                return solveHelper(nextRow, nextCol, display)
            } else {
                return false
            }
        }
        for (i in 1..9) {
            board[row][col] = i
            display.invalidate()
            if (check(row, col) && solveHelper(nextRow, nextCol, display)) {
                return true
            }
        }
        board[row][col] = 0
        return false
    }

    fun setNumberPos(num: Int) {
        if (selected_row != -1 && selected_column != -1 && editable[selected_row - 1][selected_column - 1]) {
            if (board[selected_row - 1][selected_column - 1] == num) {
                board[selected_row - 1][selected_column - 1] = 0
            } else {
                this.selectedNumber = num
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