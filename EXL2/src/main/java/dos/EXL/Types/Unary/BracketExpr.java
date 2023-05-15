package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;

public class BracketExpr implements Expression {
    
    Expression body; 

    public BracketExpr(Expression bod){
        body = bod;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "(" + body.makeString() + ")"; 
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void toASM() {

    }

}
