package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
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
    public Maybe<MyError> validate(ValueRecords records) {
        var type = value.getType(records);
        if(type.hasValue()){
            if(type.getValue().equals("boolean")){
                return new Maybe<>();
            } else {
                return new Maybe<>(new MyError("Not must be used on a boolean, " + type.getValue() + " is not valid for !"));
            }
        } else {
            return new Maybe<>(type.getError());
        }
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }
    
}

