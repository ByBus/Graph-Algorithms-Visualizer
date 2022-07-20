package visualizer.data;

public interface Savable<T> {
    T getState();
    void setState(T state);
}
