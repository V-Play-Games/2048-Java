package net.vpg.game2048;

public enum Cell {
    C0   (0,    "    ", false, 0, false),
    C2   (2,    "  2 ", true,  9, false),
    C4   (4,    "  4 ", true,  1, false),
    C8   (8,    "  8 ", false, 0, false),
    C16  (16,   " 16 ", false, 0, false),
    C32  (32,   " 32 ", false, 0, false),
    C64  (64,   " 64 ", false, 0, false),
    C128 (128,  "128 ", false, 0, false),
    C256 (256,  "256 ", false, 0, false),
    C512 (512,  "512 ", false, 0, false),
    C1024(1024, "1024", false, 0, false),
    C2048(2048, "2048", false, 0, true );

    static final Cell[] values = values();
    final String formatted;
    final int value;
    final boolean spawn;
    final int spawnRate;
    final boolean isFinal;

    Cell(int value, String formatted, boolean spawn, int spawnRate, boolean isFinal) {
        this.value = value;
        this.formatted = formatted;
        this.spawn = spawn;
        this.spawnRate = spawnRate;
        this.isFinal = isFinal;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static Cell forValue(int value) {
        assert value % 2 == 0;
        int i = 0;
        while (i < values.length && values[++i].value != value) ;
        assert i != values.length;
        return values[i];
    }

    public int getValue() {
        return value;
    }

    public String getFormatted() {
        return formatted;
    }

    public boolean isSpawn() {
        return spawn;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public Pair tryMerge(Cell other) {
        if (other == C0) return new Pair(this, C0, this != C0);
        else if (this == C0) return new Pair(other, this, false);
        else if (this == other) return new Pair(Cell.forValue(value * 2), C0, true);
        else return new Pair(other, this, false);
    }
}
