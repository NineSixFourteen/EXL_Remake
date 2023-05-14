package dos.EXL.Types.Binary.Boolean;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class GThanExpr implements Expression{
    
    public GThanExpr(Expression l , Expression r){
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
        return left.makeString() + " > "  + right.makeString();
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
    
}