package visualizer.domain.algorithm;

import visualizer.data.VertexDataModel;
import visualizer.presenter.Path;

import javax.swing.*;
import java.util.List;

public class AlgorithmWorker extends SwingWorker<Integer, Path> {
    private final Algorithm algorithm;
    private VertexDataModel start = null;
    private final Callback callback;

    public AlgorithmWorker(Algorithm algorithm, Callback callback) {
        this.algorithm = algorithm;
        this.callback = callback;
    }

    public void setStart(VertexDataModel vertex) {
        this.start = vertex;
    }

    @Override
    protected Integer doInBackground() throws InterruptedException {
        algorithm.setAction(() -> {
            Thread.sleep(400);
            publish(algorithm.getPath());
        });
        algorithm.traverse(start);
        return null;
    }

    @Override
    protected void process(List<Path> chunks) {
        super.process(chunks);
        var path = chunks.get(0);
        callback.onResult(path);
    }

    @Override
    protected void done() {
        callback.onDone(algorithm.getPath());
    }

    public void reset() {
        algorithm.reset();
    }

    public interface Callback {
        void onResult(Path path);

        void onDone(Path path);
    }
}

