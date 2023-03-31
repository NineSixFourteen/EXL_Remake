package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;

public class DeclarLine implements Line {

    public DeclarLine(String n, Expression e){
        name = n;
        value = e;
    }

    String name;
    Expression value;

    @Override
    public void accept() {
        
    } 
    
    
}
