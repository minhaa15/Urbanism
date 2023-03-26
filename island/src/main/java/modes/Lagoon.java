package modes;

import colors.IslandColors;
import shapes.Circle;
import meshcomponents.MyMesh;

public class Lagoon {
    private double radius = 200;

    public void generate(MyMesh mesh, String shape){
        Circle circle = new Circle(radius, mesh.getApproxCenterX(), mesh.getApproxCenterY());
        
        circle.setBorderColor(IslandColors.BEACH);
        circle.setInsideColor(IslandColors.LAGOON);

        circle.draw(mesh);
    }  
}
