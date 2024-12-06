package net.vpg.game2048

class Cell(var row: Int, var column: Int, val board: Board) {
    val limit = board.size - 1
    val cells get() = board.cells

    var type = CellType.C0
    var isMoved = false
    var isModified = false

    val isEmpty get() = type.isEmpty
    val isFinal get() = type.isFinal
    val value get() = type.value
    val formatted get() = type.formatted

    fun move(move: Move) = try {
        when (move) {
            Move.UP -> moveUp()
            Move.DOWN -> moveDown()
            Move.LEFT -> moveLeft()
            Move.RIGHT -> moveRight()
        }
    } catch (_: ArrayIndexOutOfBoundsException) {
        // ignore
    }

    fun moveDown() {
        val initial = row
        while (row != limit && tryMerge(cells[row + 1][column]));
        cells[initial - 1][column].moveDown()
    }

    fun moveUp() {
        val initial = row
        while (row != 0 && tryMerge(cells[row - 1][column]));
        cells[initial + 1][column].moveUp()
    }

    fun moveLeft() {
        val initial = column
        while (column != 0 && tryMerge(cells[row][column - 1]));
        cells[row][initial + 1].moveLeft()
    }

    fun moveRight() {
        val initial = column
        while (column != limit && tryMerge(cells[row][column + 1]));
        cells[row][initial - 1].moveRight()
    }

    private fun tryMerge(target: Cell) = if (this.isEmpty) {
        false
    } else if (target.isEmpty) {
        val targetRow = target.row
        val targetCol = target.column
        target.setCoordinates(this.row, this.column)
        this.setCoordinates(targetRow, targetCol)
        isMoved = true
        true
    } else if (target.type == this.type && !target.isModified) {
        target.type = CellType.forValue(this.value * 2)!!
        target.isModified = true
        this.type = CellType.C0
        isMoved = true
        false
    } else
        false

    private fun setCoordinates(row: Int, column: Int) {
        this.row = row
        this.column = column
        cells[row][column] = this
    }

    fun canMove() = (row != board.size - 1 && checkMove(cells[row + 1][column])) ||
            (row != 0 && checkMove(cells[row - 1][column])) ||
            (column != board.size - 1 && checkMove(cells[row][column + 1])) ||
            (column != 0 && checkMove(cells[row][column - 1]))

    private fun checkMove(target: Cell) = target.isEmpty || this.type == target.type
}
