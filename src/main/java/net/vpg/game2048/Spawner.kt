package net.vpg.game2048

object Spawner {
    private val spawns = CellType.entries
        .filter { it.isSpawn }
        .map { cell -> List(cell.spawnRate) { cell } }
        .flatten()

    fun Cell.spawn() = spawns.random().also { this.type = it }
}
