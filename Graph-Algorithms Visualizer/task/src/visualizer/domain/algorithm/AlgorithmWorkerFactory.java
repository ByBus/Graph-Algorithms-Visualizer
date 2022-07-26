package visualizer.domain.algorithm;

public class AlgorithmWorkerFactory {
    private final Algorithm algorithm;

    public AlgorithmWorkerFactory(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public AlgorithmWorker getInstanceWithCallback(AlgorithmWorker.WorkerCallback callback) {
        return new AlgorithmWorker(algorithm, callback);
    }
}
