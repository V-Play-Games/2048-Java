package net.vpg.game2048;

import static net.vpg.game2048.CellType.C0;

public class Cell {
    final Board board;
    final int limit;
    int row;
    int column;
    CellType type;
    boolean modified;

    public Cell(int row, int column, Board board) {
        this.row = row;
        this.column = column;
        this.board = board;
        this.limit = board.size - 1;
        this.type = C0;
    }

    public void move(Move move) {
        switch (move) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    public void moveDown() {
        int initial = row;
        while (row != limit && tryMerge(board.getCells()[row + 1][column])) ;
        if (initial != 0) board.cells[initial - 1][column].moveDown();
    }

    public void moveUp() {
        int initial = row;
        while (row != 0 && tryMerge(board.getCells()[row - 1][column])) ;
        if (initial != limit) board.cells[initial + 1][column].moveUp();
    }

    public void moveLeft() {
        int initial = column;
        while (column != limit && tryMerge(board.getCells()[row][column + 1])) ;
        if (initial != 0) board.cells[row][initial - 1].moveLeft();
    }

    public void moveRight() {
        int initial = column;
        while (column != 0 && tryMerge(board.getCells()[row][column - 1])) ;
        if (initial != limit) board.cells[row][initial + 1].moveRight();
    }

    public CellType getType() {
        return type;
    }

    public Cell setType(CellType type) {
        this.type = type;
        return this;
    }

    public int getRow() {
        return row;
    }

    public Cell setRow(int row) {
        this.row = row;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public Cell setColumn(int column) {
        this.column = column;
        return this;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isModified() {
        return modified;
    }

    public Cell setModified(boolean modified) {
        this.modified = modified;
        return this;
    }

    private boolean tryMerge(Cell target) {
        if (this.type == C0) return false;
        if (target.type == C0) {
            int temp = this.row;
            this.row = target.row;
            target.row = temp;

            temp = this.column;
            this.column = target.column;
            target.column = temp;
            board.cells[this.row][this.column] = this;
            board.cells[target.row][target.column] = target;
            return true;
        }
        if (target.type == this.type && !target.modified) {
            target.type = CellType.forValue(this.type.getValue() * 2);
            target.modified = true;
            this.type = C0;
            return false;
        }
        return false;
    }

    public boolean canMove() {
        return (row != board.size - 1 && checkMove(board.getCells()[row + 1][column])) ||
            (row != 0 && checkMove(board.getCells()[row - 1][column])) ||
            (column != board.size - 1 && checkMove(board.getCells()[row][column + 1])) ||
            (column != 0 && checkMove(board.getCells()[row][column - 1]));
    }

    private boolean checkMove(Cell target) {
        return target.type == C0 || this.type == target.type;
    }

    public boolean isEmpty() {
        return type == C0;
    }
}
