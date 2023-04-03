package dos.Types.Lines;

import java.util.List;

import dos.Types.Expression;
import dos.Types.Line;
import dos.Types.Tag;

public class Field implements Line {
    private List<Tag> tags;
    private String name; 
    private Expression expr;

    public Field(List<Tag> t, String n, Expression e){
        tags = t;
        name = n; 
        expr = e;
    }

    @Override
    public void accept() {

    }
    
}
