package dos.Types.Lines;

import java.util.ArrayList;
import java.util.List;

import dos.Types.Line;

public class CodeBlock {

    List<Line> lines; 

    public CodeBlock(){
        lines = new ArrayList<>();
    }

    public void addLine(Line l){
        lines.add(l);
    }
    
}
