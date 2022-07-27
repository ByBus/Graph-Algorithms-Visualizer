# Graph Algorithm Visualizer

Simple Swing app for creating simple graphs and applying some algorithms to them.
The following algorithms are currently supported: *Dijkstra's* (finding the shortest path), *Prim's* (finding minimum spanning tree), *Breadth-First* and *Depth-First Searches*.

#### Menu Bar

Each menu contains several functions

- File
   - *New* - clear working area
   - *Exit* - close app
- Mode
   - *Add a Vertex* - click inside working area to create vertex and enter its index in dialog pop-up
   - *Add an Edge* - click on two different vertices to create an edge. Also specify the weight of the edge and whether it is directional in the pop-up dialog.
   - *Remove a Vertex* - click a vertex to remove it.
   - *Remove an Edge* - click an edge (or its weight label) to remove it.
- Algorithms
   - *Depth-First Search* - click any vertex to traverse graph from it.  
   - *Breadth-First Search* - click any vertex to traverse graph from it.  
   - *Dijkstra's Algorithm* - click any vertex to traverse graph from it, then click any other vertex to build the shortest path to it.  
   - *Prims's Algorithm* - click any vertex to build minimum spanning tree. Prim's Algorithm works only with undirected graphs.
- Presets
   - *Circle* - creates parametric graph of circle shape
   - other presets

### Some ui screenshots

##### Modes
![](images/modes.jpg?raw=true)

##### Add an Edge dialog
![](images/addEdge.jpg?raw=true)

##### Removing elements
![](images/removingElements.gif?raw=true)

##### Applying Dijkstra's algorithm
![](images/applyingDijkstra.gif?raw=true)<br>
and finding The shortest path
![](images/shortestPathDijkstra.gif?raw=true)

##### Presets menu
![](images/presets.jpg?raw=true)

##### Circle graph preset
![](images/circleGraph.jpg?raw=true)
 Ready Circle graph
![](images/readyCircleGraph.jpg?raw=true)

##### Also you can drag vertices
![](images/drag.gif?raw=true)
