package dos.EXL.Types.Binary.Boolean;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;


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
    public Maybe<MyError> validate(DataInterface visitor) {
        return ValBoolean.validateCompare(left, right, visitor);
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface visitor){
        var val = validate(visitor);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }
}