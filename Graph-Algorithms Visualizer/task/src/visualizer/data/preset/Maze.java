package visualizer.data.preset;

import visualizer.data.Graph;
import visualizer.data.VertexDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze extends Preset{
    public Maze(Graph graph) {
        super(graph, "Maze");
    }

    @Override
    protected void creationOfGraph() {
        int step = 101;
        List<VertexDataModel> vert = new ArrayList<>();
        var random = new Random();
        char i = 'A';
        for (int x = 15; x < 800 - 50; x += step) {
            for (int y = 15; y < 600 - 100; y += step) {
                var newVert = new VertexDataModel(x, y, String.valueOf(i++));
                vert.add(newVert);
                graph.addVertex(newVert);
                vert.stream().filter(v -> (v.getX() == newVert.getX() && Math.abs(v.getY() - newVert.getY()) == step)
                                || (v.getY() == newVert.getY() && Math.abs(v.getX() - newVert.getX()) == step))
                        .forEach(v -> graph.addEdge(newVert, v, random.nextInt(9) + 1));
            }
        }
    }
}
