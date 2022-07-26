package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

import javax.swing.*;
import java.util.List;

public class AlgorithmWorker extends SwingWorker<Integer, Path> {
    private final Algorithm algorithm;
    private VertexDataModel start = null;
    private final WorkerCallback workerCallback;

    public AlgorithmWorker(Algorithm algorithm, WorkerCallback workerCallback) {
        this.algorithm = algorithm;
        this.workerCallback = workerCallback;
    }

    public void setStart(VertexDataModel vertex) {
        this.start = vertex;
    }

    @Override
    protected Integer doInBackground() throws InterruptedException {
        algorithm.setCallback(new Algorithm.Callback() {
            @Override
            public void onEveryStep() throws InterruptedException {
                Thread.sleep(200);
                publish(algorithm.getPath());
            }

            @Override
            public void onPathReady(Path path) {
                workerCallback.onResultReady(path);
            }
        });
        algorithm.traverse(start);
        return null;
    }

    @Override
    protected void process(List<Path> chunks) {
        super.process(chunks);
        var path = chunks.get(0);
        workerCallback.onResultReady(path);
    }

    @Override
    protected void done() {
        workerCallback.onDone(algorithm.getPath());
    }

    public void reset() {
        algorithm.reset();
    }

    public interface WorkerCallback {
        void onResultReady(Path path);

        void onDone(Path path);
    }
}

