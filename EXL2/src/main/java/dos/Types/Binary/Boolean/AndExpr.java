package dos.Types.Binary.Boolean;

import dos.Types.Expression;

public class AndExpr implements Expression{
    
    public AndExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }
    
}