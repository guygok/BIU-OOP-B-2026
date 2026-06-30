package tdd;

public enum Player {
    X, O, EMPTY;

    @Override
    public String toString() {
        return this == EMPTY ? "-" : name();
    }
}