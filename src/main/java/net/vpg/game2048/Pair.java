package net.vpg.game2048;

public class Pair {
    final CellType first;
    final CellType second;
    final boolean changed;

    public Pair(CellType first, CellType second, boolean changed) {
        this.first = first;
        this.second = second;
        this.changed = changed;
    }

    public CellType getFirst() {
        return first;
    }

    public CellType getSecond() {
        return second;
    }

    public boolean isChanged() {
        return changed;
    }
}
