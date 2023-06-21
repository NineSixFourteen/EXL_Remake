package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class LThanEqExpr implements BoolExpr{
    
    public LThanEqExpr(Expression l , Expression r){
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
        return left.makeString() + " <= "  + right.makeString();
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return ValBoolean.validateCompare(left, right, visitor,line);
    }

   @Override
    public void toASM(MethodInterface visitor, Primitives type) {
    }

    @Override
    public void pushInverse(Label jumpLoc) {
    }

    @Override
    public void push() {
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line){
        var val = validate(visitor,line);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }
    
}