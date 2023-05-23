package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class NotExpr implements Expression{
    
    public NotExpr(Expression v){
        value = v; 
    }

    public Expression value; 
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "!" + value.makeString();
    }

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        String type = value.getType(records);
        if(type.equals("boolean")){
            return new Maybe<>();
        } else {
            return new Maybe<Error>(new Error("Not can only be applied to a boolean type not a " + type));
        }
    }

    @Override
    public void toASM() {

    }

    @Override
    public String getType(ValueRecords records) {
        return "boolean";
    }
    
}

