package net.vpg.game2048

enum class Move(val row: Int, val column: Int, val rowChange: Int, val columnChange: Int) {
    UP   (0, 0, 0, 1),
    DOWN (1, 0, 0, 1),
    LEFT (0, 0, 1, 0),
    RIGHT(0, 1, 1, 0);
}
