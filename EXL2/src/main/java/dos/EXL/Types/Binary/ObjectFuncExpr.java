package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Maybe;

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

    @Override
    public String makeString() {
        return object.makeString() + "." + func.makeString();
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
}
