package dos.Types.Unary;

import java.util.List;

import dos.Types.Expression;

public class FunctionExpr implements Expression{
    
    public FunctionExpr(String n, List<Expression> p){
        name = n;
        params = p; 
    }

    String name;
    List<Expression> params; 
    
    @Override
    public void accept() {
        
    }
    
}
