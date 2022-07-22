package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

public interface Algorithm {
    void traverse(VertexDataModel start) throws InterruptedException;

    Path getPath();

    void setAction(SubStepAction action);

    void reset();

    interface SubStepAction {
        void run() throws InterruptedException;
    }
}
