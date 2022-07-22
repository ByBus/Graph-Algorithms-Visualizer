package visualizer.domain;

public interface Selectable {
    void select(SelectState selectState);
    SelectState getSelected();
}
