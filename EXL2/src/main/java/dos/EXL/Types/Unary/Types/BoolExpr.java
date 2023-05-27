package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.ValueRecords;

public class BoolExpr implements Expression  {

    private boolean bool; 

    public BoolExpr(boolean b){
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
    public Maybe<MyError> validate(ValueRecords records) {
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records) {
        return Results.makeResult("boolean");
    }
}
