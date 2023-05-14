package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class BoolExpr implements Expression  {

    private boolean bool; 

    public BoolExpr(boolean b){
        bool = b;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "" + bool;
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
}
