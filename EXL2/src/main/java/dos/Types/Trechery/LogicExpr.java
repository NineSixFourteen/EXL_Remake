package dos.Types.Trechery;

import dos.Types.Expression;
import dos.Types.Line;

public class LogicExpr implements Expression {

    public Expression bool;
    public Expression ifTrue;
    public Expression ifFalse;

    public LogicExpr(Expression b, Expression t, Expression f){
        bool = b;
        ifTrue = t;
        ifFalse = f;
    }

    @Override
    public void accept() {

    }
    
}
