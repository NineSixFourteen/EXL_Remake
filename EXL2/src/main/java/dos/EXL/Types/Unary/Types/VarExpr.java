package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.ValueRecords;

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
    public Maybe<MyError> validate(ValueRecords records) {
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records) {
        var info = records.getVar(name);
        if(info.hasError()){
            return Results.makeError(info.getError());
        }
        return Results.makeResult(records.getVar(name).getValue().getValue1());
    }
    

}
