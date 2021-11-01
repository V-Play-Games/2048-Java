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

    public boolean move(Move move) {
        return move(move, true);
    }

    public boolean move(Move move, boolean toMove) {
        int sign = move.forward ? 1 : -1;
        Cell[][] board = toMove ? cells : Util.deepArrayCopy(cells);
        boolean changedOverall = false;
        boolean changed;
        do {
            changed = false;
            if (move.horizontal) {
                for (int i = move.forward ? 0 : size - 1; -1 < i && i < size; i += sign) {
                    for (int j = move.forward ? size - 1 : 0; move.forward ? j > 0 : j < size - 1; j -= sign) {
                        Pair pair = board[i][j - sign].tryMerge(board[i][j]);
                        if (pair.isChanged()) {
                            board[i][j] = pair.getFirst();
                            board[i][j - sign] = pair.getSecond();

                            changed = true;
                        }
                    }
                }
            } else {
                for (int i = move.forward ? 0 : size - 1; -1 < i && i < size; i += sign) {
                    for (int j = move.forward ? size - 1 : 0; move.forward ? j > 0 : j < size - 1; j -= sign) {
                        Pair pair = board[j - sign][i].tryMerge(board[j][i]);
                        if (pair.isChanged()) {
                            board[j][i] = pair.getFirst();
                            board[j - sign][i] = pair.getSecond();
                            changed = true;
                        }
                    }
                }
            }
            changedOverall |= changed;
        } while (changed);
        if (toMove)
            spawn();
        return changedOverall;
    }

    public boolean checkLose() {
        boolean anyChange = false;
        for (Move move : Move.values()) {
            anyChange |= move(move, false);
        }
        return !anyChange;
    }

    public boolean checkWin() {
        boolean result = false;
        outer:
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (result = cell.isFinal()) {
                    break outer;
                }
            }
        }
        return result;
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
