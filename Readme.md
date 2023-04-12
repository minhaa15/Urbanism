# A4 Urbanism

  - Author: Amanbeer Minhas

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