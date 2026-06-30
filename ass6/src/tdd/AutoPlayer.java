package tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoPlayer {
    private Player symbol;
    private Random random;

    public AutoPlayer(Player symbol) {
        this.symbol = symbol;
        this.random = new Random();
    }

    public void makeMove(Board board) {
        // Collect all free cells to avoid delays on large boards (e.g., 10x10)
        List<int[]> freeCells = new ArrayList<>();
        int size = board.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isValidPosition(i, j)) {
                    freeCells.add(new int[]{i, j});
                }
            }
        }

        if (!freeCells.isEmpty()) {
            int[] choice = freeCells.get(random.nextInt(freeCells.size()));
            board.placeMark(choice[0], choice[1], symbol);
            System.out.println("AutoPlayer " + symbol + " chose position: " + choice[0] + ", " + choice[1]);
        }
    }

    public Player getSymbol() {
        return symbol;
    }
}