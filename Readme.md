# A4 Urbanism

  - Author: Amanbeer Minhas

### Rationale 

The project involves the implementation of a weighted directed graph and the shortest path algorithm in Java. 
A graph is a collection of vertices (nodes) and edges that connect the vertices. Weighted directed graphs are 
a type of graphs where the edges have a weight or cost associated with them. The shortest path algorithm is used
to find the shortest path between two nodes in a graph. The algorithm has various applications in software eng
such as in network routing, GPS navigation, and scheduling problems. For my project we are going to use it to find
the roads connecting the cities and finding the shortest paths to connect them.

### Explanation for extending the library

The Graph class implements a weighted directed graph using an adjacency list. The class provides methods for adding 
nodes and edges to the graph and retrieving the nodes and edges of the graph. The ShortestPathFinder class implements
the PathFinder algorithm using the graph data structure. Extending the library to implement the shortest path
algorithm adds additional functionality to the library. The shortest path algorithm is a fundamental algorithm in 
graph theory and has many practical applications. By adding the shortest path algorithm to the library, users can easily
find the shortest path between two nodes in a weighted directed graph. In our case we will use to connect 2 cities just
by roads but the distance will be shortest between them.
This library can be used in any of the subproject by making it as a dependency in that subproject.

## How to install?

```
%mvn install
```

It creates two jars:

  1. `generator/generator.jar` to generate meshes
  2. `visualizer/visualizer.jar` to visualize such meshes as SVG files

## Examples of execution

### Generating a mesh irregular

```

java -jar generator/generator.jar -k irregular -h 2000 -w 4000 -p 2000 -s 20 -o img/irregular.mesh

```

### generating island with cities
```
java -jar island/island.jar -i img/irregular.mesh -o img/lagoon.mesh --mode custom --shape circle --lakes 3 --aquifiers 8 --soil dessertsoil --biomes macanada --altitude mountain --rivers 10 --seed 1 --cities 39
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Visualizing a mesh, (regular or debug mode)

```
java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/irregular_debug.svg

```



Shapes: circle, rectangle, triangle
Mode: lagoon, custom
Elevation Profiles: mountain, valley
Soil Profiles: basicsoil, dessertsoil
biomes: kingsfort, macanada



### Backlog - Check Kanban 