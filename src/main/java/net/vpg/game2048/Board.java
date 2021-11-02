package net.vpg.game2048;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Board {
    final int size;
    final Cell[][] cells;
    final Random random;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j, this);
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

    public boolean checkLose() {
        return getCellsAsStream().noneMatch(Cell::canMove);
    }

    public boolean checkWin() {
        return getCellsAsStream().anyMatch(cell -> cell.getType().isFinal());
    }

    public void spawn() {
        Cell[] emptyCells = getCellsAsStream().filter(Cell::isEmpty).toArray(Cell[]::new);
        if (emptyCells.length == 0) return;
        emptyCells[random.nextInt(emptyCells.length)].setType(Spawner.getInstance().spawn());
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
