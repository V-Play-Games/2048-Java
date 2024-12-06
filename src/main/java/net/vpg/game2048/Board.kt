package net.vpg.game2048

import net.vpg.game2048.Spawner.spawn
import kotlin.math.absoluteValue

class Board(val size: Int) {
    val cells = Array<Array<Cell>>(size) { i ->
        Array<Cell>(size) { j ->
            Cell(i, j, this)
        }
    }
    val flatCells = cells.flatten()
    var score = 0
    var win = false
    var lose = false

    fun move(move: Move): Int {
        var row = move.row * (size - 1)
        var column = move.column * (size - 1)
        repeat(size, {
            cells[row][column].move(move)
            row += move.columnChange.absoluteValue
            column += move.rowChange.absoluteValue
        })
        val moveScore = flatCells
            .filter { it.isModified }
            .onEach { it.isModified = false }
            .sumOf { it.value }
        val anyMove = flatCells
            .filter { it.isMoved }
            .onEach { it.isMoved = false }
            .any()
        if (anyMove) {
            spawn()
        }
        win = flatCells.any { it.isFinal }
        lose = flatCells.none { it.canMove() }
        score += moveScore
        return moveScore
    }

    fun spawn() = flatCells.filter { it.isEmpty }
        .takeIf { it.isNotEmpty() }
        ?.random()
        ?.spawn()
}
