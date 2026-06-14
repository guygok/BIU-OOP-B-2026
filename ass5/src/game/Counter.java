package src.game;

public class Counter {
    private int count;
    public Counter(int initialValue) { this.count = initialValue; }
    public void increase(int number) { this.count += number; }
    public void decrease(int number) { this.count -= number; }
    public int getValue() { return this.count; }
}