package visualizer.data.preset;

import visualizer.data.Graph;

abstract class Preset implements GraphPreset{
    protected Callback configurationCallback;
    protected Graph graph;
    private final String name;

    public Preset(Graph graph, String name) {
        this.graph = graph;
        this.name = name;
    }

    protected abstract void creationOfGraph();

    @Override
    public void create() {
        configurationCallback.onBefore();
        creationOfGraph();
        configurationCallback.onAfter();
    }

    @Override
    public void setConfigurationCallback(Callback callback) {
        this.configurationCallback = callback;
    }

    @Override
    public String name() {
        return name;
    }
}
