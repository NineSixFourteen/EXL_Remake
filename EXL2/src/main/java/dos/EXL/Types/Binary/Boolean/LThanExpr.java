package dos.Types.Binary.Boolean;

import dos.Types.Expression;
import dos.Util.Maybe;

public class LThanExpr implements Expression{
    
    public LThanExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return left.makeString() + " < "  + right.makeString();
    }
    
    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
}