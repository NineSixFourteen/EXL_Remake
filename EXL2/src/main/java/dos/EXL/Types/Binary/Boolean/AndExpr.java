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

public class AndExpr implements BoolExpr{
    
    public AndExpr(BoolExpr l , BoolExpr r){
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
        return left.makeString() + " && "  + right.makeString();
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return ValBoolean.validateExtend(left, right, visitor,line);
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {
        Label False = new Label();
        Label after = new Label();
        MethodVisitor visit = visitor.getVisitor();
        left.pushInverse(visitor, after, False, false);
        right.pushInverse(visitor, after, False, false);
        visit.visitInsn(Opcodes.ICONST_1);
        visit.visitJumpInsn(Opcodes.GOTO, after);
        visit.visitLabel(False);
        visit.visitInsn(Opcodes.ICONST_0);
        visit.visitLabel(after);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line){
        var val = validate(visitor,line);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult("boolean");
    }


    @Override
    public void pushInverse(MethodInterface visit,Label start, Label end, boolean b) {
        Label l = new Label();
        if(left.isOr())
            left.push(visit, l, end, b);
        else 
            left.pushInverse(visit, start, end, b);
        visit.getVisitor().visitLabel(l);
        Label r = new Label();
        if(right.isOr())
            right.push(visit, r, end, b);
        else 
            right.pushInverse(visit, start, end, b);
        visit.getVisitor().visitLabel(r);
    }

    @Override
    public void push(MethodInterface visit,Label start, Label end, boolean b) {
        Label l = new Label();
        if(left.isOr())
            left.push(visit, l, end, false);
        else 
            left.pushInverse(visit,l, end, false);
        visit.getVisitor().visitLabel(l);
        right.push(visit,start, end, b);
    }

    @Override
    public boolean isOr() {
        return false;
    }

    @Override
    public boolean isAnd() {
        return true;
    }

    @Override
    public boolean isAndorOr() {
        return true;
    }

    
    
}