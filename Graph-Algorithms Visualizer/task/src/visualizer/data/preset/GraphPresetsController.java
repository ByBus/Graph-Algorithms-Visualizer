package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.Observer;

import java.util.*;

public class GraphPresetsController {
    private final Graph graph;
    private final visualizer.data.Observer[] observers;
    private final Map<String, GraphPreset> presets = new HashMap<>();

    public GraphPresetsController(Graph graph, Observer... observers) {
        this.graph = graph;
        this.observers = observers;
    }

    public void addPreset(GraphPreset preset) {
        preset.setConfigurationCallback(presetConfigurationCallback);
        presets.put(preset.name(), preset);
    }

    public void addPresets(GraphPreset... presets) {
        Arrays.stream(presets).forEach(this::addPreset);
    }

    public void create(String name) {
        presets.get(name).create();
    }

    public List<String> presetsNames() {
        return new ArrayList<>(presets.keySet());
    }

    private final GraphPreset.Callback presetConfigurationCallback = new GraphPreset.Callback() {
        @Override
        public void onBefore() {
            graph.clear();
            Arrays.stream(observers).forEach(graph::removeObserver);
        }

        @Override
        public void onAfter() {
            Arrays.stream(observers).forEach(graph::addObserver);
            graph.notifyObservers();
        }
    };
}
