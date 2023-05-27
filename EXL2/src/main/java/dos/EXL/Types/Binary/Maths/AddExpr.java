package dos.EXL.Types.Binary.Maths;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Maths.ValMaths;
import dos.EXL.Validator.Util.TypeCombiner;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.ValueRecords;

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

    @Override
    public String makeString() {
        return left.makeString() + " + "  + right.makeString();
    }

    @Override
    public Maybe<MyError> validate(ValueRecords records) {
        return ValMaths.validateMaths(left, right, records);
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
        return TypeCombiner.MathsBinary(left, right, records);
    }
    
    
}
