package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas);
        drawSegments(aMesh, canvas);
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
    }


    private void drawSegments(Mesh aMesh,Graphics2D canvas){
        List <Segment> segmentList = aMesh.getSegmentsList();
        List <Vertex> vertexList = aMesh.getVerticesList();
        
        for(Segment s : segmentList){

            //draw the line
            //Display Segments
            double v1x = vertexList.get(s.getV1Idx()).getX(), v1y = vertexList.get(s.getV1Idx()).getY();
            double v2x = vertexList.get(s.getV2Idx()).getX(), v2y = vertexList.get(s.getV2Idx()).getY();
            
            Optional<Color> fill = new ColorProperty().extract(s.getPropertiesList());

            int weight = extractWeight(s.getPropertiesList());

            canvas.setStroke(new BasicStroke(weight));
            canvas.setColor(fill.get());
            Line2D line = new Line2D.Double(new Point2D.Double(v1x, v1y), new Point2D.Double(v2x, v2y));
            canvas.draw(line);
        }   
    }

    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) {
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if(fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }


    private int extractWeight(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("weight")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 1;
        
        return Integer.parseInt(val);
    }
}
