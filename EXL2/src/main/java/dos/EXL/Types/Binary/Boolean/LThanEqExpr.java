package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;

import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Result;
import dos.Util.Results;

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
        visitor.pushBool(left, right, Opcodes.IF_ICMPLE);
    }

    @Override
    public void pushInverse(MethodInterface visitor,Label start, Label end) {
        visitor.pushJump(left, right, end, Opcodes.IF_ICMPGT);
    }

    @Override
    public void push(MethodInterface visitor,Label start, Label end) {
        visitor.pushJump(left, right, start, Opcodes.IF_ICMPLE);
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