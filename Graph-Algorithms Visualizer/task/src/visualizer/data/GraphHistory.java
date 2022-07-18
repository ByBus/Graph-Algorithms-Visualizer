package visualizer.data;

import java.util.ArrayDeque;
import java.util.Deque;

public class GraphHistory {
    private final Graph graph;
    private final Deque<Graph.GraphState> memory = new ArrayDeque<>();

    public GraphHistory(Graph graph) {
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
