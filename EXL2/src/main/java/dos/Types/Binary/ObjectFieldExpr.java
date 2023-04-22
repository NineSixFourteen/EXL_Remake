package dos.Types.Binary;

import dos.Types.Expression;

public class ObjectFieldExpr implements Expression  {
    
    Expression object;
    String fieldCall; 

    public ObjectFieldExpr(Expression left, String fieldName){
        object = left;
        fieldCall = fieldName; 
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return object.makeString() + "." + fieldCall;
    }

}
