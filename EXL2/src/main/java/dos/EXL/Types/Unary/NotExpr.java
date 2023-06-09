package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
    public Maybe<MyError> validate(DataInterface visitor) {
        var type = value.getType(visitor);
        if(type.hasValue()){
            if(type.getValue().equals("boolean")){
                return new Maybe<>();
            } else {
                return new Maybe<>(ErrorFactory.makeLogic("Not must be used on a boolean, " + type.getValue() + " is not valid for !",3));
            }
        } else {
            return new Maybe<>(type.getError());
        }
    }

    @Override
    public void toASM(MethodInterface visitor) {

    }

    @Override
    public Result<String> getType(DataInterface visitor) {
        var val = validate(visitor);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }
    
}

