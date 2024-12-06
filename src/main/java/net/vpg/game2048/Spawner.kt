package net.vpg.game2048;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Spawner {
    private static final Spawner instance = new Spawner();
    final Random random;
    final List<CellType> spawnables;
    final int size;

    private Spawner() {
        this.random = new Random();
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

    public static Spawner getInstance() {
        return instance;
    }

    public CellType spawn() {
        return spawnables.get(random.nextInt(size));
    }

    public void spawn(Cell cell) {
        cell.setType(spawn());
    }
}
