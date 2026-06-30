package tdd;

public class Board {
    private Player[][] grid;
    private int size;

    public Board(int size) {
        this.size = size;
        this.grid = new Player[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = Player.EMPTY;
            }
        }
    }

    public boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        return grid[row][col] == Player.EMPTY;
    }

    public void placeMark(int row, int col, Player player) {
        if (isValidPosition(row, col)) {
            grid[row][col] = player;
        }
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == Player.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public Player checkWin() {
        // Check rows and columns
        for (int i = 0; i < size; i++) {
            if (checkLine(0, i, 1, 0) != Player.EMPTY) return grid[0][i]; // Col
            if (checkLine(i, 0, 0, 1) != Player.EMPTY) return grid[i][0]; // Row
        }
        // Check diagonals
        if (checkLine(0, 0, 1, 1) != Player.EMPTY) return grid[0][0];
        if (checkLine(0, size - 1, 1, -1) != Player.EMPTY) return grid[0][size - 1];

        return Player.EMPTY;
    }

    private Player checkLine(int startRow, int startCol, int dRow, int dCol) {
        Player first = grid[startRow][startCol];
        if (first == Player.EMPTY) return Player.EMPTY;

        for (int i = 1; i < size; i++) {
            if (grid[startRow + i * dRow][startCol + i * dCol] != first) {
                return Player.EMPTY;
            }
        }
        return first;
    }

    public Player[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}