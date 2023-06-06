package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.FunctionVisitor;

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
    public Maybe<MyError> validate(FunctionVisitor visitor) {
        var info = visitor.getVar(name);
        if(info.hasError())
            return new Maybe<>(info.getError());
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(FunctionVisitor visitor) {
        var x = validate(visitor);
        if(x.hasValue())
            return Results.makeError(x.getValue());
        return Results.makeResult(visitor.getVar(name).getValue().getValue1());
    }
    

}
