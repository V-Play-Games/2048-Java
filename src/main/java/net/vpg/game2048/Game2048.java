package net.vpg.game2048;

import java.util.Optional;
import java.util.Scanner;

import static net.vpg.game2048.Move.*;

public class Game2048 {
    public static final Optional<String> WIN = Optional.of("You won!");
    public static final Optional<String> LOSE = Optional.of("You are out of moves! Game Over.");

    public static void main(String[] args) {
        String help = "Commands:\n" +
            "U to swipe up\n" +
            "D to swipe down\n" +
            "L to swipe left\n" +
            "R to swipe right\n" +
            "E to end the game\n" +
            "H for help";
        Scanner in = new Scanner(System.in);
        System.out.println("Enter size of board");
        Board board = new Board(in.nextInt());
        board.spawn();
        board.spawn();
        System.out.println(help);
        System.out.println(board);
        boolean playing = true;
        do {
            switch (in.next().charAt(0)) {
                case 'E':
                case 'e':
                    playing = false;
                    break;
                case 'H':
                case 'h':
                    System.out.println(help);
                    break;
                case 'u':
                case 'U':
                    playing = move(board, UP);
                    break;
                case 'd':
                case 'D':
                    playing = move(board, DOWN);
                    break;
                case 'l':
                case 'L':
                    playing = move(board, LEFT);
                    break;
                case 'r':
                case 'R':
                    playing = move(board, RIGHT);
                    break;
                default:
                    System.out.println("Invalid Command!");
                    break;
            }
        } while (playing);
        System.out.println("Thanks for playing!");
    }

    public static boolean move(Board board, Move move) {
        board.move(move);
        System.out.println(board);
        System.out.println("Score: " + board.getScore());
        boolean win = board.checkWin();
        boolean lose = board.checkLose();
        WIN.filter(s -> win).ifPresent(System.out::println);
        LOSE.filter(s -> lose).ifPresent(System.out::println);
        return !win && !lose;
    }
}
