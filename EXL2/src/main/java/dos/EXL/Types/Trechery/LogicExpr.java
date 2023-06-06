package dos.EXL.Types.Trechery;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.ValueRecords;

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
        return bool.makeString() + " ? " + ifTrue.makeString() + " : " + ifFalse ;
    }

    @Override
    public Maybe<MyError> validate(ValueRecords records) {
        var trueT = ifTrue.getType(records);
        var falseT = ifFalse.getType(records);
        var boolT = bool.getType(records);
        if(boolT.hasError())
            return new Maybe<MyError>(boolT.getError());
        if(trueT.hasError())
            return new Maybe<>(trueT.getError());
        if(falseT.hasError())
            return new Maybe<>(falseT.getError());
        if(!boolT.getValue().equals("boolean"))
            return new Maybe<>(ErrorFactory.makeLogic("First part of logic expression has to be boolean", 2));
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
