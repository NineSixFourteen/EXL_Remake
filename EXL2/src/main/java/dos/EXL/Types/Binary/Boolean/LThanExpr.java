package dos.EXL.Types.Binary.Boolean;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Result;
import dos.Util.Results;


public class LThanExpr implements Expression{
    
    public LThanExpr(Expression l , Expression r){
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
        return left.makeString() + " < "  + right.makeString();
    }
    
    @Override
    public Maybe<Error> validate(ValueRecords records) {
        return ValBoolean.validateCompare(left, right, records);
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String,Error> getType(ValueRecords records){
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }
}