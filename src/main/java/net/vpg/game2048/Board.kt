package net.vpg.game2048;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
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
        int moveScore = getCellsAsStream()
            .filter(Cell::isModified)
            .peek(Cell::removeModifyFlag)
            .mapToInt(Cell::getValue)
            .sum();
        if (moveScore != 0) {
            spawn();
        }
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
        Cell[] emptyCells = getCellsAsStream().filter(Cell::isEmpty).toArray(Cell[]::new);
        if (emptyCells.length != 0)
            Spawner.getInstance().spawn(emptyCells[random.nextInt(emptyCells.length)]);
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        String boundary = "+------".repeat(size) + "+\n";
        return Arrays.stream(cells)
            .map(row -> Arrays.stream(row)
                .map(Cell::getFormatted)
                .collect(Collectors.joining(" | ", "| ", " |\n")))
            .collect(Collectors.joining("", boundary, boundary));
    }
}
