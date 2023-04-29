package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;
import dos.Util.IndentMaker;

public class DeclarLine implements Line {

    public DeclarLine(String n, String t, Expression e){
        name = n;
        value = e;
        type = t;
    }

    String name;
    String type;
    Expression value;

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + type + " "+ name + " = " + value + ";\n";
    } 
    
    
}
