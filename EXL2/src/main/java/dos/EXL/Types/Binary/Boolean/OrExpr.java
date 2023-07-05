package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class OrExpr implements BoolExpr {
    
    public OrExpr(BoolExpr l , BoolExpr r){
        left = l; 
        right = r;
    }

    public BoolExpr left; 
    public BoolExpr right;
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return left.makeString() + " || "  + right.makeString();
    }

    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return ValBoolean.validateExtend(left, right, visitor,line);
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {
        Label False = new Label();
        Label True = new Label();
        Label after = new Label();
        left.push(visitor, True, False, true);
        right.push(visitor, True, False, true);
        MethodVisitor visit = visitor.getVisitor();
        visit.visitLabel(True);
        visit.visitInsn(Opcodes.ICONST_1);
        visit.visitJumpInsn(Opcodes.GOTO, after);
        visit.visitLabel(False);
        visit.visitInsn(Opcodes.ICONST_0);
        visit.visitLabel(after);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line){
        var val = validate(visitor,line);
        if(val.hasValue())
            return Results.makeError(val.getValue());
        return Results.makeResult("boolean");
    }

    @Override
    public void pushInverse(MethodInterface visitor,Label start, Label end, boolean b) {
        left.pushInverse(visitor, start, end,true);
        right.push(visitor, start, end,true);
    }

    @Override
    public void push(MethodInterface visitor,Label start, Label end, boolean b) {
        Label l = new Label();
        left.push(visitor, start, l,true);
        visitor.getVisitor().visitLabel(l);
        if (right.isOr())
            right.push(visitor, start, end,b); 
        else if (right.isAnd() && b)
            right.push(visitor, start, end,b); 
        else
            right.pushInverse(visitor, start, end, b);
    }

    @Override
    public boolean isOr() {
        return true;
    }

    @Override
    public boolean isAnd() {
        return false;
    }

    @Override
    public boolean isAndorOr() {
        return true;
    }
    
}