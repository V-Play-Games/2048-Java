package net.vpg.game2048;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Board {
    final int size;
    Cell[][] cells;
    Cell[][] alt;
    final Random random;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.alt = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                new Cell(i, j, this);
            }
        }
        this.random = new Random();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Stream<Cell> getCellsAsStream() {
        return Arrays.stream(cells).flatMap(Arrays::stream);
    }

    public void move(Move move) {
        int row = move.row * (size - 1);
        int column = move.column * (size - 1);
        for (int runs = 0; runs < size; runs++, row += move.rowChange, column += move.columnChange) {
            cells[row][column].move(move);
        }
        spawn();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                cell.setModified(false);
            }
        }
    }

    public void move2(Move move) {
        switch (move) {
            case LEFT:
                reverse();
                move2(Move.RIGHT);
                reverse();
                return;
            case DOWN:
                transpose();
                move2(Move.LEFT);
                transpose();
                return;
            case UP:
                transpose();
                move2(Move.RIGHT);
                transpose();
                return;
        }
        for (int i = 0; i < size; i++) {
            cells[i][0].moveRight();
        }
        spawn();
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                cell.setModified(false);
            }
        }
    }

    void transpose() {
        getCellsAsStream().forEach(Cell::transpose);
        switchArrays();
    }

    void reverse() {
        getCellsAsStream().forEach(Cell::reverse);
        switchArrays();
    }

    private void switchArrays() {
        Cell[][] backup = alt;
        alt = cells;
        cells = backup;
    }

    public boolean checkLose() {
        return getCellsAsStream().noneMatch(Cell::canMove);
    }

    public boolean checkWin() {
        return getCellsAsStream().anyMatch(cell -> cell.getType().isFinal());
    }

    public void spawn() {
        Optional.of(getCellsAsStream().filter(Cell::isEmpty).toArray(Cell[]::new))
            .filter(emptyCells  -> emptyCells.length != 0)
            .ifPresent(emptyCells -> emptyCells[random.nextInt(emptyCells.length)].setType(Spawner.getInstance().spawn()));
    }

    public String toString() {
        StringBuilder tor = new StringBuilder();
        tor.append('+');
        tor.append("------+".repeat(size));
        int firstLineLen = tor.length();
        tor.append('\n');
        for (Cell[] row : cells) {
            tor.append('|');
            for (Cell cell : row) {
                tor.append(' ').append(cell.getType().getFormatted()).append(' ').append('|');
            }
            tor.append('\n');
        }
        tor.append(tor, 0, firstLineLen);
        return tor.toString();
    }
}
