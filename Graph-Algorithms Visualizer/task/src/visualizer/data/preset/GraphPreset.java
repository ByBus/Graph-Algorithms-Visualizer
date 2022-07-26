package visualizer.data.preset;

public interface GraphPreset {
    void create();

    String name();

    void setConfigurationCallback(Callback callback);

    interface Callback {
        void onBefore();

        void onAfter();
    }
}
