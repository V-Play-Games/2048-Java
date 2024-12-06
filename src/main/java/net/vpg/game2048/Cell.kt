package net.vpg.game2048

class Cell(var row: Int, var column: Int, val board: Board) {
    val cells get() = board.cells

    var type = CellType.C0
    var isMoved = false
    var isModified = false

    val isEmpty get() = type.isEmpty
    val isFinal get() = type.isFinal
    val value get() = type.value
    val formatted get() = type.formatted

    fun move(move: Move) {
        val (r0, c0) = Pair(row, column)
        while (tryMerge(row + move.rowChange, column + move.columnChange));
        cell(r0 - move.rowChange, c0 - move.columnChange)?.move(move)
    }

    private fun cell(r: Int, c: Int) =
        if (r < 0 || r >= cells.size || c < 0 || c >= cells.size) null else cells[r][c]

    private fun tryMerge(r: Int, c: Int) = cell(r, c)?.let { target ->
        if (this.isEmpty) {
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
    } == true

    private fun setCoordinates(row: Int, column: Int) {
        this.row = row
        this.column = column
        cells[row][column] = this
    }

    fun canMove() = checkMove(row + 1, column) ||
            checkMove(row - 1, column) ||
            checkMove(row, column + 1) ||
            checkMove(row, column - 1)

    private fun checkMove(r: Int, c: Int) = cell(r, c)?.let { target ->
        target.isEmpty || this.type == target.type
    } == true
}
