package dos.Types.Binary.Maths;

import dos.Types.Expression;

public class SubExpr implements Expression{
    
    public SubExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }
    
}