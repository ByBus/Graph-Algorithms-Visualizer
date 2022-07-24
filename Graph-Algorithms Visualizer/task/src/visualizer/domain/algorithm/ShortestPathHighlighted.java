package visualizer.domain.algorithm;

import visualizer.domain.SelectState;

public class ShortestPathHighlighted extends SpanningTree {
    private final SelectState selectionType = SelectState.HIGHLIGHTED;
    private int distance = 0;

    {
        super.selectionType = selectionType;
    }

    @Override
    public String toString() {
        return "Distance: " + distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
