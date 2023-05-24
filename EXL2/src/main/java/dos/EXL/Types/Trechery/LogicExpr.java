package dos.EXL.Types.Trechery;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import dos.Util.Result;
import dos.Util.Results;

public class LogicExpr implements Expression {

    public Expression bool;
    public Expression ifTrue;
    public Expression ifFalse;

    public LogicExpr(Expression b, Expression t, Expression f){
        bool = b;
        ifTrue = t;
        ifFalse = f;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString() {
        throw new UnsupportedOperationException("Unimplemented method 'makeString'");
    }

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        var trueT = ifTrue.getType(records);
        var falseT = ifFalse.getType(records);
        if(trueT.hasError()){
            return new Maybe<>(trueT.getError());
        } else if(falseT.hasError()){
            return new Maybe<>(falseT.getError());
        }
        return trueT.getValue().equals(falseT.getValue()) 
                    ? new Maybe<>()
                    : new Maybe<>(new Error("Both side of : have to be the same type left side type - "
                                                        + trueT.getValue() + " right side - "
                                                        + falseT.getValue())) ;
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String,Error> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }       
        return Results.makeResult(ifTrue.getType(records).getValue());
    }
    
}
