package dos.EXL.Types.Binary.Boolean;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.ValueRecords;


public class NotEqExpr implements Expression{
    
    public NotEqExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return left.makeString() + " != "  + right.makeString();
    }
    
    @Override
    public Maybe<MyError> validate(ValueRecords records) {
        return ValBoolean.validateCompare(left, right, records);
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records){
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }

}