package dos.Types.Lines;

import java.util.List;

import dos.Types.Expression;
import dos.Types.Line;

public class WhileLine implements Line {

    public WhileLine(DeclarLine d, Expression b, Line l, List<Line> ls){
        bool = b;
        lines = ls;
    }

    Expression bool;
    List<Line> lines;

    @Override
    public void accept() {

    } 
    
    
}