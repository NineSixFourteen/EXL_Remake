package dos.EXL.Types.Trechery;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
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
    public Maybe<MyError> validate(ValueRecords records) {
        var trueT = ifTrue.getType(records);
        var falseT = ifFalse.getType(records);
        if(trueT.hasError()){
            return new Maybe<>(trueT.getError());
        } else if(falseT.hasError()){
            return new Maybe<>(falseT.getError());
        }
        return trueT.getValue().equals(falseT.getValue()) 
                    ? new Maybe<>()
                    : new Maybe<>(ErrorFactory.makeLogic("Both side of : have to be the same type left side type - "
                                                        + trueT.getValue() + " right side - "
                                                        + falseT.getValue(),5)) ;
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }       
        return Results.makeResult(ifTrue.getType(records).getValue());
    }
    
}
