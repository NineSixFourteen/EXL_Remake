package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;

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
    
    
}
