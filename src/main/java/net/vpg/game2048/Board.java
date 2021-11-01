package net.vpg.game2048;

import java.util.Arrays;
import java.util.Random;

public class Board {
    final int size;
    final Cell[][] cells;
    final Random random;
    final Spawner spawner;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.C0);
        }
        this.random = new Random();
        this.spawner = new Spawner(random);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void move(Move move) {
        int sign = move.forward ? 1 : -1;
        boolean changed;
        do {
            changed = false;
            if (move.horizontal) {
                for (int i = move.forward ? 0 : size - 1; -1 < i && i < size; i += sign) {
                    for (int j = move.forward ? size - 1 : 0; move.forward ? j > 0 : j < size - 1; j -= sign) {
                        Pair pair = cells[i][j - sign].tryMerge(cells[i][j]);
                        if (pair.isChanged()) {
                            cells[i][j] = pair.getFirst();
                            cells[i][j - sign] = pair.getSecond();
                            changed = true;
                        }
                    }
                }
            } else {
                for (int i = move.forward ? 0 : size - 1; -1 < i && i < size; i += sign) {
                    for (int j = move.forward ? size - 1 : 0; move.forward ? j > 0 : j < size - 1; j -= sign) {
                        Pair pair = cells[j - sign][i].tryMerge(cells[j][i]);
                        if (pair.isChanged()) {
                            cells[j][i] = pair.getFirst();
                            cells[j - sign][i] = pair.getSecond();
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);
        spawn();
    }

    public void spawn() {
        int empty = 0;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell == Cell.C0) {
                    empty++;
                }
            }
        }
        if (empty == 0) return;
        int spawnIndex = random.nextInt(empty);
        empty = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = cells[i][j];
                if (cell == Cell.C0) {
                    if (empty == spawnIndex) {
                        cells[i][j] = spawner.spawn();
                    }
                    empty++;
                }
            }
        }
    }

    public String toString() {
        StringBuilder tor = new StringBuilder();
        tor.append('+');
        tor.append("------+".repeat(size));
        tor.append('\n');
        for (Cell[] row : cells) {
            tor.append('+');
            for (Cell cell : row) {
                tor.append(' ').append(cell.getFormatted()).append(' ').append('+');
            }
            tor.append('\n');
        }
        tor.append('+');
        tor.append("------+".repeat(size));
        return tor.toString();
    }
}
