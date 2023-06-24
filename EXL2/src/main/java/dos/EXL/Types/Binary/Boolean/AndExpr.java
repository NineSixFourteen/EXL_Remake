package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.MyError;
import dos.EXL.Validator.Boolean.ValBoolean;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
        left.pushInverse(visitor, after, False);
        right.pushInverse(visitor, after, False);
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
    public void pushInverse(MethodInterface visit,Label end, Label start) {
        left.pushInverse(visit, start, end);
        right.pushInverse(visit, start, end);
    }

    @Override
    public void push(MethodInterface visit,Label end, Label start) {
        left.pushInverse(visit,start, end);
        right.push(visit,start, end);
    }
    
}