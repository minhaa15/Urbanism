package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

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


public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }

        ArrayList <Segment> segmentList = new ArrayList<>(aMesh.getSegmentsList());
        ArrayList <Vertex> vertexList = new ArrayList<>(aMesh.getVerticesList());

        System.out.println("ENTERED");

        for (int x = 0; x < segmentList.size(); x++) {
            double v1x = vertexList.get(segmentList.get(x).getV1Idx()).getX(), v1y = vertexList.get(segmentList.get(x).getV1Idx()).getY();
            double v2x = vertexList.get(segmentList.get(x).getV2Idx()).getX(), v2y = vertexList.get(segmentList.get(x).getV2Idx()).getY();
            
            System.out.println("" + segmentList.get(x).getV1Idx() + ", " + segmentList.get(x).getV2Idx());

            canvas.setColor(extractColor(segmentList.get(x).getPropertiesList()));
            Line2D line = new Line2D.Double(new Point2D.Double(v1x, v1y), new Point2D.Double(v2x, v2y));
            canvas.draw(line);
        }
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

}
