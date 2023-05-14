package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class NotExpr implements Expression{
    
    public NotExpr(Expression v){
        value = v; 
    }

    public Expression value; 
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "!" + value.makeString();
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void compileASM() {

    }
    
}

