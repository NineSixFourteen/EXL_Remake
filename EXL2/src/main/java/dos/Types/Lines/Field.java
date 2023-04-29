package dos.Types.Lines;

import java.util.List;

import dos.Types.Expression;
import dos.Types.Line;
import dos.Types.Tag;
import dos.Util.IndentMaker;

public class Field implements Line {
    private List<Tag> tags;
    private String name; 
    private String type;
    private Expression expr;

    public Field(List<Tag> t, String n, Expression e, String ty){
        tags = t;
        name = n; 
        expr = e;
        type = ty;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        for(Tag t : tags){
            res += t.name() + " ";
        }
        res += type + " ";
        res += name + " = ";
        res += expr.makeString() + ";\n";
        return res;
    }
    
}
