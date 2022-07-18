package visualizer.domain.algorithm;

public class AlgorithmWorkerFactory {
    Algorithm algorithm;

    public AlgorithmWorkerFactory(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public AlgorithmWorker getInstanceWithCallback(AlgorithmWorker.Callback callback) {
        return new AlgorithmWorker(algorithm, callback);
    }
}
