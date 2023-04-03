package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;

public class WhileLine implements Line {

    public WhileLine(Expression b, CodeBlock ls){
        bool = b;
        body = ls;
    }

    Expression bool;
    CodeBlock body;

    @Override
    public void accept() {

    } 
    
    
}