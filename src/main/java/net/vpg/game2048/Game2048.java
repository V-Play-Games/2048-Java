package net.vpg.game2048;

import java.util.Scanner;

public class Game2048 {
    public static void main(String[] args) throws InterruptedException {
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
        Thread.sleep(2000);
        char command;
        String partingMessage = "";
        do {
            System.out.println(board);
            command = in.next().charAt(0);
            if (command == 'E' || command == 'e') {
                break;
            } else if (command == 'H' || command == 'h') {
                System.out.println(help);
            } else {
                Move move = Move.fromKey(command);
                if (move == null) {
                    System.out.println("Invalid Command!");
                    continue;
                }
                board.move(move);
                if (board.checkWin()) {
                    partingMessage = "You won!";
                    break;
                }
                if (board.checkLose()) {
                    partingMessage = "You are out of moves! Game Over.";
                    break;
                }
            }
        } while (true);
        if (command != 'E' && command != 'e') {
            System.out.println(board);
            System.out.println(partingMessage);
        }
        System.out.println("Thanks for playing!");
    }
}
