package net.vpg.game2048;

public enum Move {
    UP   (0, 0, 0, 1),
    DOWN (1, 0, 0, 1),
    LEFT (0, 0, 1, 0),
    RIGHT(0, 1, 1, 0);

    final int row;
    final int column;
    final int rowChange;
    final int columnChange;

    Move(int row, int column, int rowChange, int columnChange) {
        this.row = row;
        this.column = column;
        this.rowChange = rowChange;
        this.columnChange = columnChange;
    }

    public static Move fromKey(char key) {
        switch (key) {
            case 'u':
            case 'U':
                return UP;
            case 'd':
            case 'D':
                return DOWN;
            case 'l':
            case 'L':
                return LEFT;
            case 'r':
            case 'R':
                return RIGHT;
            default:
                return null;
        }
    }
}
