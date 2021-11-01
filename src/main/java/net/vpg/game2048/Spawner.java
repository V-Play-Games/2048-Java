package net.vpg.game2048;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Spawner {
    final Random random;
    final List<Cell> spawnables;
    final int size;

    public Spawner(Random random) {
        this.random = random;
        this.spawnables = Arrays.stream(Cell.values())
            .filter(Cell::isSpawn)
            .map(cell -> {
                Cell[] cells = new Cell[cell.getSpawnRate()];
                Arrays.fill(cells, cell);
                return cells;
            })
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());
        size = spawnables.size();
    }

    public Cell spawn() {
        return spawnables.get(random.nextInt(size));
    }
}
