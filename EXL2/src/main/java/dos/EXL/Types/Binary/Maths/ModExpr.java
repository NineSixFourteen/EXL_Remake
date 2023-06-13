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

public class ModExpr implements Expression{
    
    public ModExpr(Expression l , Expression r){
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
        return left.makeString() + " % "  + right.makeString();
    }
    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return ValMaths.validateMaths(left, right, visitor,line);
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type, int line) {
        visitor.push(left,type,line);
        visitor.push(right, type,line);
        visitor.doMath(type, Symbol.Mod);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var val = validate(visitor,line);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return TypeCombiner.MathsBinary(left, right, visitor,line);
    }
    
}