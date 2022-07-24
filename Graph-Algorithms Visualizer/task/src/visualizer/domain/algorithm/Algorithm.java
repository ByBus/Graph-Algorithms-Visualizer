package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

public interface Algorithm {
    void traverse(VertexDataModel start) throws InterruptedException;

    Path getPath();

    void setAction(Action action);

    void executeStepAction();

    void reset();

    interface Action {
        void onEveryStep() throws InterruptedException;
        void onEnd(Path path);
    }
}
