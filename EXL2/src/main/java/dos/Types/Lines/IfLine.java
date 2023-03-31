package dos.Types.Lines;

import java.util.List;

import dos.Types.Expression;
import dos.Types.Line;

public class IfLine implements Line {

    public IfLine(Expression e, List<Line> ls){
        val = e;
        lines = ls;
    }
    
    private Expression val;
    private List<Line> lines;

    @Override
    public void accept() {
        
    }
    
}
