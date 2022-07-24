package visualizer.domain.usecases;

import visualizer.data.GraphHistory;
import visualizer.domain.Command;
import visualizer.domain.algorithm.AlgorithmWorker;
import visualizer.domain.algorithm.AlgorithmWorkerFactory;
import visualizer.presenter.GraphCanvas;
import visualizer.presenter.LabelMaster;
import visualizer.presenter.Path;
import visualizer.presenter.VertexUI;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TraverseGraphUseCase implements Command {
    private AlgorithmWorker traverser = null;
    private final LabelMaster labelMaster;
    private final GraphCanvas canvas;
    private final GraphHistory history;
    private final AlgorithmWorkerFactory factory;
    private Boolean isStarted = false;
    public TraverseGraphUseCase(AlgorithmWorkerFactory factory,
                                LabelMaster labelMaster,
                                GraphCanvas canvas,
                                GraphHistory history) {
        this.factory = factory;
        this.labelMaster = labelMaster;
        this.canvas = canvas;
        this.history = history;
        init();
    }

    private void init() {
        traverser = factory.getInstanceWithCallback(callback);
    }

    @Override
    public void reset() {
        init();
        traverser.reset();
        history.restore();
        labelMaster.hide();
        isStarted = false;
    }

    @Override
    public void execute(Component component, MouseEvent event) {
        if (!isStarted && component instanceof VertexUI) {
            labelMaster.waitMessage();
            isStarted = true;
            history.save();
            traverser.setStart(((VertexUI) component).toDataModel());
            traverser.execute();
        }
    }

    private final AlgorithmWorker.Callback callback = new AlgorithmWorker.Callback() {
        @Override
        public void onResult(Path path) {
            canvas.updateComponents(path.toComponents());
        }

        @Override
        public void onDone(Path path) {
            labelMaster.showPath(path);
            isStarted = false;
            init();
        }
    };
}
