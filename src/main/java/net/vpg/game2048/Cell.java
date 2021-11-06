package net.vpg.game2048;

import java.util.Optional;

import static net.vpg.game2048.CellType.C0;

public class Cell {
    final Board board;
    final int limit;
    Cell[][] cells;
    int row;
    int column;
    CellType type;
    boolean modified;

    public Cell(int row, int column, Board board) {
        this.cells = board.cells;
        this.board = board;
        this.limit = board.size - 1;
        this.type = C0;
        setCoordinates(row, column);
    }

    void move(Move move) {
        try {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore
        }
    }

    void moveDown() {
        int initial = row;
        while (row != limit && tryMerge(cells[row + 1][column])) ;
        cells[initial - 1][column].moveDown();
    }

    void moveUp() {
        int initial = row;
        while (row != 0 && tryMerge(cells[row - 1][column])) ;
        cells[initial + 1][column].moveUp();
    }

    void moveLeft() {
        int initial = column;
        while (column != 0 && tryMerge(cells[row][column - 1])) ;
        cells[row][initial + 1].moveLeft();
    }

    void moveRight() {
        int initial = column;
        while (column != limit && tryMerge(cells[row][column + 1])) ;
        cells[row][initial - 1].moveRight();
    }

    public CellType getType() {
        return type;
    }

    void setType(CellType type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isModified() {
        return modified;
    }

    public void removeModifyFlag() {
        this.modified = false;
    }

    private boolean tryMerge(Cell target) {
        // check if this is empty
        return !this.isEmpty() &&
            // or this is equal to target
            Optional.of(true)
                .filter(b -> target.isEmpty())
                .map(b -> {
                    int targetRow = target.row;
                    int targetCol = target.column;
                    target.setCoordinates(this.row, this.column);
                    this.setCoordinates(targetRow, targetCol);
                    return true;
                })
                .or(() -> Optional.of(false)
                    .filter(b -> target.type == this.type && !target.modified)
                    .map(b -> {
                        target.type = CellType.forValue(this.getValue() * 2);
                        target.modified = true;
                        this.type = C0;
                        return false;
                    }))
                .orElse(false);
    }

    private void setCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
        cells[row][column] = this;
    }

    public boolean canMove() {
        return (row != board.size - 1 && checkMove(cells[row + 1][column])) ||
            (row != 0 && checkMove(cells[row - 1][column])) ||
            (column != board.size - 1 && checkMove(cells[row][column + 1])) ||
            (column != 0 && checkMove(cells[row][column - 1]));
    }

    private boolean checkMove(Cell target) {
        return target.isEmpty() || this.type == target.type;
    }

    public boolean isEmpty() {
        return type.isEmpty();
    }

    public boolean isFinal() {
        return type.isFinal;
    }

    public int getValue() {
        return type.value;
    }

    public String getFormatted() {
        return type.formatted;
    }
}
