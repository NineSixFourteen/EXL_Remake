package dos.EXL.Types.Unary.Types;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class BooleanExpr implements BoolExpr  {

    private boolean bool; 

    public BooleanExpr(boolean b){
        bool = b;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "" + bool;
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visit,Primitives type) {
        MethodVisitor visitor = visit.getVisitor();
        if(bool)
            visitor.visitInsn(Opcodes.ICONST_1);
        else 
            visitor.visitInsn(Opcodes.ICONST_0);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        return Results.makeResult("boolean");
    }

     @Override
    public void pushInverse(MethodInterface visit,Label start, Label end, boolean b) {
        MethodVisitor visitor = visit.getVisitor();
        if(bool)
            visitor.visitInsn(Opcodes.ICONST_1);
        else 
            visitor.visitInsn(Opcodes.ICONST_0);
        visitor.visitJumpInsn(Opcodes.IFEQ, end);
    }

    @Override
    public void push(MethodInterface visit,Label start, Label end, boolean b) {
        MethodVisitor visitor = visit.getVisitor();
        if(bool)
            visitor.visitInsn(Opcodes.ICONST_1);
        else 
            visitor.visitInsn(Opcodes.ICONST_0);
        visitor.visitJumpInsn(Opcodes.IFNE, start);
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
