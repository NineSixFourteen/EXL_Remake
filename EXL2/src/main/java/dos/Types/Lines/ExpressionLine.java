package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;

public class ExpressionLine implements Line  {

    private Expression expr;

    public ExpressionLine(Expression e){
        expr = e;
    }

    @Override
    public void accept() {
        
    }
    
}
