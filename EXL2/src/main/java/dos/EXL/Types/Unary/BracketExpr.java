package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class BracketExpr implements Expression {
    
    Expression body; 

    public BracketExpr(Expression bod){
        body = bod;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "(" + body.makeString() + ")"; 
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor) {
        return body.validate(visitor);
    }

    @Override
    public void toASM(MethodInterface visitor) {

    }

    @Override
    public Result<String> getType(DataInterface visitor) {
        return body.getType(visitor);
    }

}
