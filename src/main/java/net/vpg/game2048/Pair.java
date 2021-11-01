package net.vpg.game2048;

public class Pair {
    final Cell first;
    final Cell second;
    final boolean changed;

    public Pair(Cell first, Cell second, boolean changed) {
        this.first = first;
        this.second = second;
        this.changed = changed;
    }

    public Cell getFirst() {
        return first;
    }

    public Cell getSecond() {
        return second;
    }

    public boolean isChanged() {
        return changed;
    }
}
