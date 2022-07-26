package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

public interface Algorithm {
    void traverse(VertexDataModel start) throws InterruptedException;

    Path getPath();

    void setCallback(Callback callback);

    void executeStepAction();

    void reset();

    interface Callback extends StepAction {
        void onPathReady(Path path);
    }

    interface StepAction {
        void onEveryStep() throws InterruptedException;
    }

}
