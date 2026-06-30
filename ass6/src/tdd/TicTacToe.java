package tdd;

public class TicTacToe {
    private Board board;
    private int playerXWins = 0;
    private int playerOWins = 0;
    private int size;
    private int numHumanPlayers;

    public TicTacToe(int size, int numHumanPlayers) {
        this.size = size;
        this.numHumanPlayers = numHumanPlayers;
        this.board = new Board(size);
    }

    public boolean verifyBoardSize(int boardSize) {
        return boardSize >= 3 && boardSize <= 10;
    }

    public void handleWinner(Player winner) {
        if (winner == Player.X) {
            playerXWins++;
            System.out.println("Player X Wins!");
        } else if (winner == Player.O) {
            playerOWins++;
            System.out.println("Player O Wins!");
        } else {
            System.out.println("It's a Tie!");
        }
        printScoreBoard();
    }

    private void printScoreBoard() {
        System.out.println("\n--- Score Board ---");
        System.out.println("Player X: " + playerXWins + " wins");
        System.out.println("Player O: " + playerOWins + " wins");
        System.out.println("-------------------\n");
    }

    public void resetBoard() {
        this.board = new Board(size);
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayerXWins() { return playerXWins; }
    public int getPlayerOWins() { return playerOWins; }
    public int getNumHumanPlayers() { return numHumanPlayers; }
}