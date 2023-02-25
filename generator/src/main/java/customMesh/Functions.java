package customMesh;
import java.awt.Color;
import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import java.util.Arrays;
public class Functions {
    public static String extractColor(Color color){
        String rgb = color.getRed() + "," + color.getGreen()  + "," + color.getBlue() + "," + color.getAlpha();
        return rgb;
    }
    public static Color extractColorFromProperties(List<Property> properties) {
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
    }//end of method extractColor

    public static MyVertex exists(List <MyVertex> vs, MyVertex mv){
        for(MyVertex v : vs){
            if(v.equals(mv)){
                return v;
            }
        }
        return null;
    }
    public static MySegment exists(List <MySegment> ss, MySegment ms){
        for(MySegment s : ss){
            if(s.equals(ms)){
                return s;
            }
        }
        return null;
    }
}