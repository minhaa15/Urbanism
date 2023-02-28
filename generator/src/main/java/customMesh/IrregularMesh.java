package customMesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

public class IrregularMesh extends MeshADT{
    private int numberOfPolygons;
    private double height; 
    private double width; 
    private float lineThickness;
    private float vertexThickness;
    private int lloydRelaxation;

    public IrregularMesh(int presicion, int numberOfPolygons, double height, double width, float vertexThickness, float lineThickness, int lloydRelaxation){
        this.numberOfPolygons = numberOfPolygons;
        this.height = height;
        this.width = width;
        this.presicion = presicion;
        this.vertexThickness = vertexThickness;
        this.lineThickness = lineThickness;
        this.lloydRelaxation = lloydRelaxation;

        vertices = new ArrayList<>();
        segments = new ArrayList<>();
        polygons = new ArrayList<>();
    }


    @Override
    public void generate() {
    }
}