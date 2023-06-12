package dos.EXL.Types.Binary.Maths;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Compiler.ASM.Util.Symbol;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Maths.ValMaths;
import dos.EXL.Validator.Util.TypeCombiner;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
    public Maybe<MyError> validate(DataInterface visitor) {
        return ValMaths.validateMaths(left, right, visitor);
    }

    @Override
    public void toASM(MethodInterface visitor, Primitives type) {
        visitor.push(left,type);
        visitor.push(right, type);
        visitor.doMath(type, Symbol.Plus);
    }

    @Override
    public Result<String> getType(DataInterface visitor) {
        var val = validate(visitor);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return TypeCombiner.MathsBinary(left, right, visitor);
    }
    
    
}
