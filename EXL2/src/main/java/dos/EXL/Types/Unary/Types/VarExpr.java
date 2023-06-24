package dos.EXL.Types.Unary.Types;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class VarExpr implements BoolExpr{

    String name;

    public VarExpr(String str){
        name = str;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString() {
        return name;
    }
    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        var info = visitor.getVar(name,line);
        if(info.hasError())
            return new Maybe<>(info.getError());
        var type = info.getValue().getType();
        if(!isbasic(type)){
            var exists = visitor.getFullImport(type);
            if(exists.hasError())
                return new Maybe<>(exists.getError());
        }

        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {
        visitor.pushVar(name);
    }

    private boolean isbasic(String type){
        switch(type){
            case "int":
            case "float":
            case "long":
            case "double":
            case "boolean":
            case "char":
            case "short":
            case "String":
            case "string":
                return true;
            default:
                return false;
        }
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var x = validate(visitor,line);
        if(x.hasValue())
            return Results.makeError(x.getValue());
        return Results.makeResult(visitor.getVar(name,line).getValue().getType());
    }

    @Override
    public void pushInverse(MethodInterface visit,Label start, Label end) {
        MethodVisitor visitor = visit.getVisitor();
        visit.pushVar(name);
        visitor.visitJumpInsn(Opcodes.IFNE, end);
    }

    @Override
    public void push(MethodInterface visit,Label start, Label end) {
        MethodVisitor visitor = visit.getVisitor();
        visit.pushVar(name);
        visitor.visitJumpInsn(Opcodes.IFEQ, start);
    }
    

}
