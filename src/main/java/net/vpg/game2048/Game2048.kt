package net.vpg.game2048

import java.util.*

object Game2048 {
    @JvmStatic
    fun main(args: Array<String>) {
        val help = """
            Commands:
            U to swipe up
            D to swipe down
            L to swipe left
            R to swipe right
            E to end the game
            H for help
            """.trimIndent()
        val scan = Scanner(System.`in`)
        println("Enter size of board")
        val board = Board(scan.nextInt())
        board.spawn()
        board.spawn()
        println(help)
        println(board)
        var playing = true
        do {
            when (scan.next().lowercase()[0]) {
                'e' -> playing = false
                'h' -> println(help)
                'u' -> playing = move(board, Move.UP)
                'd' -> playing = move(board, Move.DOWN)
                'l' -> playing = move(board, Move.LEFT)
                'r' -> playing = move(board, Move.RIGHT)
                else -> println("Invalid Command!")
            }
        } while (playing)
        println("Thanks for playing!")
    }

    fun move(board: Board, move: Move): Boolean {
        board.move(move)
        println(board)
        println("Score: ${board.score}")
        if (board.win) {
            println("You won!")
        }
        if (board.lose) {
            println("You are out of moves! Game Over.")
        }
        return !board.win && !board.lose
    }
}
