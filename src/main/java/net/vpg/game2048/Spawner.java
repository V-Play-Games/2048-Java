package net.vpg.game2048;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Spawner {
    final Random random;
    final List<CellType> spawnables;
    final int size;

    public Spawner(Random random) {
        this.random = random;
        this.spawnables = Arrays.stream(CellType.values())
            .filter(CellType::isSpawn)
            .map(cell -> {
                CellType[] cells = new CellType[cell.getSpawnRate()];
                Arrays.fill(cells, cell);
                return cells;
            })
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());
        size = spawnables.size();
    }

    public CellType spawn() {
        return spawnables.get(random.nextInt(size));
    }
}
