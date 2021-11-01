package net.vpg.game2048;

import java.util.Scanner;

public class Game2048 {
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
        String command;
        do {
            System.out.println(board);
            command = in.next();
            if (command.charAt(0) == 'E' || command.charAt(0) == 'e') {
                System.out.println("Thanks for playing!");
            } else if (command.charAt(0) == 'H' || command.charAt(0) == 'h') {
                System.out.println(help);
            } else {
                Move move = Move.fromKey(command);
                if (move == null) {
                    System.out.println("Invalid Command!");
                    continue;
                }
                board.move(move);
            }
        } while (command.charAt(0) != 'E' && command.charAt(0) != 'e');
    }
}
