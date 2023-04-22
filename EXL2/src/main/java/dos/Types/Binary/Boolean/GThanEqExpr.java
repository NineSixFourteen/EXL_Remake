package dos.Types.Binary.Boolean;

import dos.Types.Expression;

public class GThanEqExpr implements Expression{
    
    public GThanEqExpr(Expression l , Expression r){
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
        return left.makeString() + " >= "  + right.makeString();
    }
    
}