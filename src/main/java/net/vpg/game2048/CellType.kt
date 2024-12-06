package net.vpg.game2048

import net.vpg.game2048.CellType.entries
import kotlin.math.ln

enum class CellType(val value: Int, val formatted: String, val isSpawn: Boolean, val spawnRate: Int, val isFinal: Boolean) {
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

    val isEmpty = value == 0

    companion object {
        @JvmStatic
        fun forValue(value: Int) = (ln(value.toDouble()) / ln(2.0)).let {
            if (it % 1 != 0.0 || it > entries.size - 1)
                null
            else
                entries[it.toInt()]
        }
    }
}
