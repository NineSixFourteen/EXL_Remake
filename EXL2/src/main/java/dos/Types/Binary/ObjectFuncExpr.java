package dos.Types.Binary;

import dos.Types.Expression;
import dos.Types.Unary.FunctionExpr;

public class ObjectFuncExpr implements Expression{

    Expression object;
    FunctionExpr func; 

    public ObjectFuncExpr(Expression left, FunctionExpr fe){
        object = left;
        func = fe;
    }
    
    @Override
    public void accept() {
        
    }
}
