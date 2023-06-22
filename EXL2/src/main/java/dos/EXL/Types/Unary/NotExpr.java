package dos.EXL.Types.Unary;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class NotExpr implements BoolExpr{
    
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
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        var type = value.getType(visitor,line);
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
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var val = validate(visitor,line);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }

    @Override
    public void pushInverse(MethodVisitor visit,Label jump1, Label Jump2) {
    }

    @Override
    public void push(MethodVisitor visit,Label jump1, Label Jump2) {
    }
    
}

