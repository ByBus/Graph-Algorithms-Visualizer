package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class MinimumSpanningTree extends SpanningTree {
    private final Map<VertexDataModel, VertexDataModel> printBuffer = new HashMap<>();

    @Override
    public void addEdge(VertexDataModel start, VertexDataModel end, int weight) {
        super.addEdge(start, end, weight);
        printBuffer.put(start, end);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        printBuffer.keySet().stream()
                .sorted(Comparator.comparing(VertexDataModel::getIndex))
                .map(v -> v + "=" + printBuffer.get(v))
                .forEach(joiner::add);
        return joiner.toString();
    }

    @Override
    public void clear() {
        super.clear();
        printBuffer.clear();
    }
}

