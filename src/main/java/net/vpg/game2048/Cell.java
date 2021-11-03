package net.vpg.game2048;

import static net.vpg.game2048.CellType.C0;

public class Cell {
    final Board board;
    final int limit;
    Cell[][] current;
    Cell[][] spare;
    int row;
    int column;
    CellType type;
    boolean modified;

    public Cell(int row, int column, Board board) {
        this.current = board.cells;
        this.spare = board.alt;
        this.board = board;
        this.limit = board.size - 1;
        this.type = C0;
        setCoordinates(row, column);
        spare[row][column] = this;
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
        while (row != limit && tryMerge(current[row + 1][column])) ;
        if (initial != 0) current[initial - 1][column].moveDown();
    }

    public void moveUp() {
        int initial = row;
        while (row != 0 && tryMerge(current[row - 1][column])) ;
        if (initial != limit) current[initial + 1][column].moveUp();
    }

    public void moveLeft() {
        int initial = column;
        while (column != limit && tryMerge(current[row][column + 1])) ;
        if (initial != 0) current[row][initial - 1].moveLeft();
    }

    public void moveRight() {
        int initial = column;
        while (column != 0 && tryMerge(current[row][column - 1])) ;
        if (initial != limit) current[row][initial + 1].moveRight();
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    private boolean tryMerge(Cell target) {
        if (this.type == C0) return false;
        if (target.type == C0) {
            int targetRow = target.row;
            int targetCol = target.column;
            target.setCoordinates(this.row, this.column);
            this.setCoordinates(targetRow, targetCol);
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

    void transpose() {
        switchArrays();
        setCoordinates(column, row);
    }

    void reverse() {
        switchArrays();
        setCoordinates(row, limit - column);
    }

    private void switchArrays() {
        Cell[][] backup = spare;
        spare = current;
        current = backup;
    }

    void setCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
        current[row][column] = this;
    }

    public boolean canMove() {
        return (row != board.size - 1 && checkMove(current[row + 1][column])) ||
            (row != 0 && checkMove(current[row - 1][column])) ||
            (column != board.size - 1 && checkMove(current[row][column + 1])) ||
            (column != 0 && checkMove(current[row][column - 1]));
    }

    private boolean checkMove(Cell target) {
        return target.type == C0 || this.type == target.type;
    }

    public boolean isEmpty() {
        return type.isEmpty();
    }
}
