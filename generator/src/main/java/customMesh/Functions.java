package customMesh;
import java.awt.Color;
import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    public static int findId(List <MyVertex> mv, MyVertex v){
        int x = 0;
        for(MyVertex i : mv){
            if(i.equals(v)){
                return x;
            }
            x++;
        }
        return -1;
    }

    public void findSegments(List <MyVertex> mv){
        Collections.sort(mv, new Comparator<MyVertex>(){
            @Override
            public int compare(MyVertex v1, MyVertex v2) {
                if (v1.getXPosition() < v2.getXPosition()) {
                    return -1;
                } else if (v1.getXPosition() > v2.getXPosition()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }
}

