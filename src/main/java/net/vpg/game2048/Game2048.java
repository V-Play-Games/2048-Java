package net.vpg.game2048;

import java.util.Scanner;

import static net.vpg.game2048.Move.*;

public class Game2048 {
    public static void main(String[] args) {
        String help = """
            Commands:
            U to swipe up
            D to swipe down
            L to swipe left
            R to swipe right
            E to end the game
            H for help""";
        Scanner in = new Scanner(System.in);
        System.out.println("Enter size of board");
        Board board = new Board(in.nextInt());
        board.spawn();
        board.spawn();
        System.out.println(help);
        System.out.println(board);
        boolean playing = true;
        do {
            switch (in.next().toLowerCase().charAt(0)) {
                case 'e' -> playing = false;
                case 'h' -> System.out.println(help);
                case 'u' -> playing = move(board, UP);
                case 'd' -> playing = move(board, DOWN);
                case 'l' -> playing = move(board, LEFT);
                case 'r' -> playing = move(board, RIGHT);
                default -> System.out.println("Invalid Command!");
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
        if (win) {
            System.out.println("You won!");
        }
        if (lose) {
            System.out.println("You are out of moves! Game Over.");
        }
        return !win && !lose;
    }
}
