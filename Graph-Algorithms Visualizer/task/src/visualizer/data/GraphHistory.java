package visualizer.data;

import java.util.ArrayDeque;
import java.util.Deque;

public class GraphHistory<T> {
    private final Savable<T> graph;
    private final Deque<T> memory = new ArrayDeque<>();

    public GraphHistory(Savable<T> graph) {
        this.graph = graph;
    }

    public void save() {
        memory.push(graph.getState());
    }

    public void restore() {
        if (!memory.isEmpty()) {
            graph.setState(memory.pop());
        }
    }

    public void clear() {
        memory.clear();
    }
}
