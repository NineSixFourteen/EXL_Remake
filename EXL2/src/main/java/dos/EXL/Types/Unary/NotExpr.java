package dos.EXL.Types.Unary;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class NotExpr implements BoolExpr{
    
    public NotExpr(BoolExpr v){
        value = v; 
    }

    public BoolExpr value; 
    
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
        MethodVisitor visit = visitor.getVisitor();
        Label True = new Label();
        Label after = new Label();
        value.pushInverse(visitor, after, True);
        visit.visitInsn(Opcodes.ICONST_0);
        visit.visitJumpInsn(Opcodes.GOTO, after);
        visit.visitLabel(True);
        visit.visitInsn(Opcodes.ICONST_1);
        visit.visitLabel(after);
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
    public void pushInverse(MethodInterface visit,Label start, Label end) {
        value.push(visit, start, end);
    }

    @Override
    public void push(MethodInterface visit,Label start, Label end) {
        value.pushInverse(visit, start, end);
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

