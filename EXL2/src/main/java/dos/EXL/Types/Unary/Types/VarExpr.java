package dos.EXL.Types.Unary.Types;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class VarExpr implements Expression{

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
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type, int line) {

    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var x = validate(visitor,line);
        if(x.hasValue())
            return Results.makeError(x.getValue());
        return Results.makeResult(visitor.getVar(name,line).getValue().getType());
    }
    

}
