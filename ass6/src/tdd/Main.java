package tdd;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to TicTacToe!");

        int size = 0;
        while (true) {
            System.out.print("Enter board size (3-10): ");
            size = scanner.nextInt();
            TicTacToe tempGame = new TicTacToe(3, 2); // Temp object to test size
            if (tempGame.verifyBoardSize(size)) {
                break;
            }
            System.out.println("Invalid board size. Must be between 3 and 10.");
        }

        int numPlayers = -1;
        while (numPlayers < 0 || numPlayers > 2) {
            System.out.print("How many human players [0-2]? ");
            numPlayers = scanner.nextInt();
        }

        TicTacToe game = new TicTacToe(size, numPlayers);
        AutoPlayer autoX = (numPlayers == 0) ? new AutoPlayer(Player.X) : null;
        AutoPlayer autoO = (numPlayers <= 1) ? new AutoPlayer(Player.O) : null;

        boolean playing = true;

        while (playing) {
            game.resetBoard();
            Player currentPlayer = Player.X;
            boolean gameWon = false;

            while (!game.getBoard().isFull() && !gameWon) {
                game.getBoard().printBoard();
                System.out.println("Player " + currentPlayer + "'s turn.");

                if (currentPlayer == Player.X && autoX != null) {
                    autoX.makeMove(game.getBoard());
                } else if (currentPlayer == Player.O && autoO != null) {
                    autoO.makeMove(game.getBoard());
                } else {
                    // Human Move
                    boolean validMove = false;
                    while (!validMove) {
                        System.out.print("Enter row and column (e.g., 0 1): ");
                        int r = scanner.nextInt();
                        int c = scanner.nextInt();
                        if (game.getBoard().isValidPosition(r, c)) {
                            game.getBoard().placeMark(r, c, currentPlayer);
                            validMove = true;
                        } else {
                            System.out.println("Invalid move. Try again.");
                        }
                    }
                }

                Player winner = game.getBoard().checkWin();
                if (winner != Player.EMPTY) {
                    game.getBoard().printBoard();
                    game.handleWinner(winner);
                    gameWon = true;
                } else if (game.getBoard().isFull()) {
                    game.getBoard().printBoard();
                    game.handleWinner(Player.EMPTY); // Handles the tie logic
                }

                currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
            }

            System.out.print("Play another game? (y/n): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("y")) {
                playing = false;
                System.out.println("Thanks for playing!");
            }
        }
        scanner.close();
    }
}