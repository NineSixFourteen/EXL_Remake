package dos.Types.Lines;

import java.util.List;

import dos.Types.Expression;
import dos.Types.Line;

public class ForLine implements Line {

    public ForLine(DeclarLine d, Expression b, Line l, List<Line> ls){
        dec = d;
        bool = b;
        line = l;
        lines = ls;
    }

    DeclarLine dec;
    Expression bool;
    Line line;
    List<Line> lines;

    @Override
    public void accept() {
    } 
    
    
}
