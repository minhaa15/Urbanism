package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas, boolean debug) {

        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        

        if(debug) {
            drawDebug(aMesh, canvas);
        }
        else {
            drawNormal(aMesh, canvas);
        }        
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null){
            return Color.BLACK;
        }
            
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);

        Color nColor = new Color(red, green, blue, alpha);
        return nColor;
    }
    private void drawNormal(Mesh aMesh, Graphics2D canvas){
        List <Polygon> polygonList = new ArrayList<>(aMesh.getPolygonsList());
        List <Segment> segmentList = new ArrayList<>(aMesh.getSegmentsList());
        List <Vertex> vertexList = new ArrayList<>(aMesh.getVerticesList());

        for(Polygon p : polygonList){
            List <Integer> polySegs = p.getSegmentIdxsList();
            List <Integer> polyVert = new ArrayList<>();
            List <Segment> polySegments  = new ArrayList<>();

            for(int i : polySegs){
                Segment s = segmentList.get(i);
                polySegments.add(s);
                polyVert.add(s.getV1Idx());

                //draw the line
                //Display Segments
                double v1x = vertexList.get(s.getV1Idx()).getX(), v1y = vertexList.get(s.getV1Idx()).getY();
                double v2x = vertexList.get(s.getV2Idx()).getX(), v2y = vertexList.get(s.getV2Idx()).getY();

                Color segmentColor = extractColor(s.getPropertiesList());
                
                System.out.println("Property LIST LENGTH" + s.getPropertiesList());

                canvas.setColor(extractColor(s.getPropertiesList()));

                Line2D line = new Line2D.Double(new Point2D.Double(v1x, v1y), new Point2D.Double(v2x, v2y));
                canvas.draw(line);
            }
        }
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
    }

    private void drawDebug(Mesh aMesh, Graphics2D canvas) {
        List <Polygon> polygonList = new ArrayList<>(aMesh.getPolygonsList());
        List <Segment> segmentList = new ArrayList<>(aMesh.getSegmentsList());
        List <Vertex> vertexList = new ArrayList<>(aMesh.getVerticesList());

        for(Polygon p : polygonList){ 
            List <Integer> polySegs = p.getSegmentIdxsList();
            List <Integer> polyVert = new ArrayList<>();

            List <Segment> polySegments  = new ArrayList<>();
    
            
            for(int i : polySegs){
                Segment s = segmentList.get(i);
                polySegments.add(s);
                polyVert.add(s.getV1Idx());

                //draw the line
                //Display Segments
                double v1x = vertexList.get(s.getV1Idx()).getX(), v1y = vertexList.get(s.getV1Idx()).getY();
                double v2x = vertexList.get(s.getV2Idx()).getX(), v2y = vertexList.get(s.getV2Idx()).getY();
                canvas.setColor(new Color(0, 0, 0));
                Line2D line = new Line2D.Double(new Point2D.Double(v1x, v1y), new Point2D.Double(v2x, v2y));
                canvas.draw(line);
                //
            }


            for (int i: polyVert) {
                Vertex v = vertexList.get(i);
                double centre_x = v.getX() - (THICKNESS/2.0d);
                double centre_y = v.getY() - (THICKNESS/2.0d);
                Color old = canvas.getColor();
                canvas.setColor(new Color(0, 0, 0));
                Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
                canvas.fill(point);
                canvas.setColor(old);
            }

            Vertex v = vertexList.get(p.getCentroidIdx());
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(new Color(255, 0, 0));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);

            //neighbours
            List<Integer> neighbourIds = p.getNeighborIdxsList();

            for(int n : neighbourIds){
                Vertex nextCentroid = vertexList.get(polygonList.get(n).getCentroidIdx());
                double v1x = v.getX(), v1y = v.getY();
                double v2x = nextCentroid.getX(), v2y = nextCentroid.getY();
                canvas.setColor(new Color(173, 173, 173));
                Line2D line = new Line2D.Double(new Point2D.Double(v1x, v1y), new Point2D.Double(v2x, v2y));
                canvas.draw(line);
            }
        }           
    }

}