package dos.EXL.Types.Trechery;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
public class LogicExpr implements BoolExpr {

    public BoolExpr bool;
    public Expression ifTrue;
    public Expression ifFalse;

    public LogicExpr(BoolExpr b, Expression t, Expression f){
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
        Label False = new Label();
        Label after = new Label();
        MethodVisitor visit = visitor.getVisitor();
        bool.pushInverse(visitor, after, False);
        ifTrue.toASM(visitor, type);
        visit.visitJumpInsn(Opcodes.GOTO, after);
        visit.visitLabel(False);
        ifFalse.toASM(visitor, type);
        visit.visitLabel(after);
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
    public void pushInverse(MethodInterface visit, Label jump1, Label jump2) {
    }

    @Override
    public void push(MethodInterface visit, Label jump1, Label jump2) {
    }

    @Override
    public boolean isOr() {
        return false;
    }

    @Override
    public boolean isAnd() {
        return false;
    }

    @Override
    public boolean isAndorOr() {
        return false;
    }
    
}
