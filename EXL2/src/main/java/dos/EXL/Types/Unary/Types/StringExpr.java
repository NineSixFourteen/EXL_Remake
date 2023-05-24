package dos.EXL.Types.Unary.Types;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.ValueRecords;

public class StringExpr implements Expression {

    private String val; 

    public StringExpr(String s){
        val = s;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public String makeString() {
        return "\"" + val + "\"";
    }

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String,Error> getType(ValueRecords records) {
        return Results.makeResult("String");
    }
}
