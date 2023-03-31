package dos.Types.Unary;

import java.util.List;

import dos.Types.Expression;

public class FunctionExpr implements Expression{
    
    public FunctionExpr(List<Expression> p){
        params = p; 
    }

    public List<Expression> params; 
    
    @Override
    public void accept() {
        
    }
    
}
