package dos.Types.Binary.Boolean;

import dos.Types.Expression;

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
    
}