import net.vpg.game2048.Board;
import net.vpg.game2048.Move;

public class SpeedTest {
    public static void main(String[] args) {
        Board board1 = new Board(4);
        Board board2 = new Board(4);
        int moves1 = 0;
        int moves2 = 0;
        long nano1 = System.nanoTime();
        do {
            for (Move move : Move.values()) {
                board1.move(move);
            }
            moves1 += 4;
        } while (!board1.checkLose());
        long nano2 = System.nanoTime();
        do {
            for (Move move : Move.values()) {
                board2.move2(move);
            }
            moves2 += 4;
        } while (!board1.checkLose());
        long nano3 = System.nanoTime();
        long average1 = (nano2 - nano1) / moves1;
        long average2 = (nano3 - nano2) / moves2;
        System.out.println("First:" + average1 + "\nSecond:" + average2);
    }
}
