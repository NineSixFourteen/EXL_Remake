package dos.Types.Unary.Types;

import dos.Types.Expression;

public class BoolExpr implements Expression  {

    private boolean bool; 

    public BoolExpr(boolean b){
        bool = b;
    }

    @Override
    public void accept() {
        
    }
}
