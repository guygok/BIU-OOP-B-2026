package tdd;

import org.junit.Test;
import static org.junit.Assert.*;

public class TicTacToeTest {

    @Test
    public void checkWinTest() {
        Board board = new Board(3);
        // Test Row Win
        board.placeMark(0, 0, Player.X);
        board.placeMark(0, 1, Player.X);
        board.placeMark(0, 2, Player.X);
        assertEquals(Player.X, board.checkWin());

        // Test Diagonal Win
        Board diagBoard = new Board(3);
        diagBoard.placeMark(0, 0, Player.O);
        diagBoard.placeMark(1, 1, Player.O);
        diagBoard.placeMark(2, 2, Player.O);
        assertEquals(Player.O, diagBoard.checkWin());
    }

    @Test
    public void handleWinnerTest() {
        TicTacToe game = new TicTacToe(3, 2);
        game.handleWinner(Player.X);
        assertEquals(1, game.getPlayerXWins());
        assertEquals(0, game.getPlayerOWins());

        game.handleWinner(Player.O);
        assertEquals(1, game.getPlayerXWins());
        assertEquals(1, game.getPlayerOWins());
    }

    @Test
    public void isValidPositionTest() {
        Board board = new Board(3);
        assertTrue(board.isValidPosition(0, 0));
        assertTrue(board.isValidPosition(2, 2));

        // Out of bounds
        assertFalse(board.isValidPosition(-1, 0));
        assertFalse(board.isValidPosition(3, 3));

        // Already taken
        board.placeMark(1, 1, Player.X);
        assertFalse(board.isValidPosition(1, 1));
    }

    @Test
    public void isFullTest() {
        Board board = new Board(3);
        assertFalse(board.isFull());

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board.placeMark(i, j, Player.X);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    public void verifyBoardSizeTest() {
        TicTacToe game = new TicTacToe(3, 2);
        assertTrue(game.verifyBoardSize(3));
        assertTrue(game.verifyBoardSize(10));

        // Invalid sizes
        assertFalse(game.verifyBoardSize(2));
        assertFalse(game.verifyBoardSize(11));
    }
}
