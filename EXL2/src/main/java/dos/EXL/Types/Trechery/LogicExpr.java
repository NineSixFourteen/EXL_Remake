package dos.EXL.Types.Trechery;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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
        return null;
    }

    @Override
    public void toASM() {

    }

    @Override
    public String getType(ValueRecords records) {
        return ifTrue.getType(records);
    }
    
}
