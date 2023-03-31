package dos.Types.Binary.Maths;

import dos.Types.Expression;

public class AddExpr implements Expression{
    
    public AddExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }
    
}
