package dos.EXL.Types.Binary.Boolean;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.FunctionVisitor;


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
    public Maybe<MyError> validate(FunctionVisitor visitor) {
        return ValBoolean.validateCompare(left, right, visitor);
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(FunctionVisitor visitor){
        var val = validate(visitor);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }
}