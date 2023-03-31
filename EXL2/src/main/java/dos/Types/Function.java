package dos.Types;

import java.util.List;

public class Function {
    
    List<Tag> tags; 
    String type;
    List<Line> lines; 

    public Function(List<Tag> t, String ty, List<Line> l){
        tags = t;
        type = ty;
        lines = l;
    }
}
