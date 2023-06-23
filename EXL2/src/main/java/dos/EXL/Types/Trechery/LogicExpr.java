package dos.EXL.Types.Trechery;

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
public class LogicExpr implements BoolExpr {

    public Expression bool;
    public Expression ifTrue;
    public Expression ifFalse;

    public LogicExpr(Expression b, Expression t, Expression f){
        bool = b;
        ifTrue = t;
        ifFalse = f;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString() {
        return bool.makeString() + " ? " + ifTrue.makeString() + " : " + ifFalse ;
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        var trueT = ifTrue.getType(visitor,line);
        var falseT = ifFalse.getType(visitor,line);
        var boolT = bool.getType(visitor,line);
        if(boolT.hasError())
            return new Maybe<MyError>(boolT.getError());
        if(trueT.hasError())
            return new Maybe<>(trueT.getError());
        if(falseT.hasError())
            return new Maybe<>(falseT.getError());
        if(!boolT.getValue().equals("boolean"))
            return new Maybe<>(ErrorFactory.makeLogic("First part of logic expression has to be boolean", 2));
        return trueT.getValue().equals(falseT.getValue()) 
                    ? new Maybe<>()
                    : new Maybe<>(ErrorFactory.makeLogic("Both side of : have to be the same type left side type - "
                                                        + trueT.getValue() + " right side - "
                                                        + falseT.getValue(),5)) ;
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface FunctionVisitor, int line) {
        var val = validate(FunctionVisitor,line);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }       
        return Results.makeResult(ifTrue.getType(FunctionVisitor,line).getValue());
    }

    @Override
    public void pushInverse(MethodVisitor visit, Label jump1, Label jump2) {
    }

    @Override
    public void push(MethodVisitor visit, Label jump1, Label jump2) {
    }
    
}
