package dos.Types.Binary.Maths;

import dos.Types.Expression;

public class MulExpr implements Expression{
    
    public MulExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }
    
}