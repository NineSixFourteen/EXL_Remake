package dos.Types.Binary.Boolean;

import dos.Types.Expression;

public class OrExpr implements Expression{
    
    public OrExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }
    
}