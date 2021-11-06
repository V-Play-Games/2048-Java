package net.vpg.game2048;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class Board {
    final int size;
    final Random random;
    Cell[][] cells;
    int score;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
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

    public int move(Move move) {
        int row = move.row * (size - 1);
        int column = move.column * (size - 1);
        for (int runs = 0; runs < size; runs++) {
            cells[row][column].move(move);
            row += move.rowChange;
            column += move.columnChange;
        }
        spawn();
        int moveScore = getCellsAsStream()
            .filter(Cell::isModified)
            .peek(Cell::removeModifyFlag)
            .mapToInt(Cell::getValue)
            .sum();
        score += moveScore;
        return moveScore;
    }

    public boolean checkLose() {
        return getCellsAsStream().noneMatch(Cell::canMove);
    }

    public boolean checkWin() {
        return getCellsAsStream().anyMatch(Cell::isFinal);
    }

    void spawn() {
        Optional.of(getCellsAsStream().filter(Cell::isEmpty).toArray(Cell[]::new))
            .filter(emptyCells -> emptyCells.length != 0)
            .ifPresent(emptyCells -> Spawner.getInstance().spawn(emptyCells[random.nextInt(emptyCells.length)]));
    }

    public int getScore() {
        return score;
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
                tor.append(' ').append(cell.getFormatted()).append(' ').append('|');
            }
            tor.append('\n');
        }
        tor.append(tor, 0, firstLineLen);
        return tor.toString();
    }
}
