package tdd;

import org.junit.Test;
import static org.junit.Assert.*;

public class AutoPlayerTest {

    @Test
    public void autoPlayerMakeMoveTest() {
        Board board = new Board(3);
        AutoPlayer auto = new AutoPlayer(Player.O);

        // Ensure the board is not full initially
        assertFalse(board.isFull());

        auto.makeMove(board);

        // Count marks to ensure exactly one move was made
        int marks = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board.getGrid()[i][j] != Player.EMPTY) {
                    marks++;
                }
            }
        }
        assertEquals(1, marks);
    }
}