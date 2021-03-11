package battleship;

import java.util.Scanner;

public class Game {

    Battleship player1;
    Battleship player2;

    public Game() {
        player1 = new Battleship();
        player2 = new Battleship();
    }

    public void play() {

        System.out.println("Player 1, place your ships on the game field");
        player1.inputPrompt();
        passTurn();
        System.out.println("Player 2, place your ships on the game field");
        player2.inputPrompt();
        int turn = 0;
        while(player1.remainingHits() > 0 && player2.remainingHits() > 0) {
            passTurn();
            ++turn;
            if (turn % 2 == 1) {
                player1.printFields();
                System.out.println("Player 1, it's your turn:");
                player1.Hit(player2);
            } else {
                player2.printFields();
                System.out.println("Player 2, it's your turn:");
                player2.Hit(player1);
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static void passTurn() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner scanner = new Scanner(System.in);
        String newLine = scanner.nextLine();
        if ("\r\n".equals(newLine)) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
