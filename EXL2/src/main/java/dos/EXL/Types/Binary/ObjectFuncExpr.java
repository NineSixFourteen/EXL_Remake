package dos.EXL.Types.Binary;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
public class ObjectFuncExpr implements BoolExpr{

    Expression object;
    FunctionExpr func; 

    public ObjectFuncExpr(Expression left, FunctionExpr fe){
        object = left;
        func = fe;
    }
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return object.makeString() + "." + func.makeString();
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        var leftType = object.getType(visitor,line);
        if(leftType.hasError())
            return new Maybe<>(leftType.getError());
        var fun = func.getPDesc(visitor, line);
        if(fun.hasError())
            return new Maybe<MyError>(fun.getError());
        var classData = visitor.getfuncType(leftType.getValue(), func.getName(), fun.getValue());
        if(classData.hasError())
            return new Maybe<MyError>(classData.getError());
        return new Maybe<>();
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
        var leftType = object.getType(visitor,line);
        if(leftType.hasError()){
            return leftType;
        }
        var fun = func.getPDesc(visitor, line);
        if(fun.hasError())
            return fun;
        return visitor.getfuncType(leftType.getValue(), func.getName(), fun.getValue());
       
    }

    @Override
    public void pushInverse(MethodVisitor visit, Label jump1, Label jump2) {
    }

    @Override
    public void push(MethodVisitor visit, Label jump1, Label jump2) {
    }
}
