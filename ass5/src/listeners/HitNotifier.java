package src.listeners;

public interface HitNotifier {
    void addHitListener(HitListener hl);
    void removeHitListener(HitListener hl);
}