package visualizer.domain;

public enum Mode {
    ADD_VERTEX("Current Mode -> Add a Vertex"),
    ADD_EDGE("Current Mode -> Add an Edge"),
    NONE("Current Mode -> None");

    public final String label;

    Mode(String label) {
        this.label = label;
    }
}
